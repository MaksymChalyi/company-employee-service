package com.maksimkaxxl.springbootrestfulapi.services.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maksimkaxxl.springbootrestfulapi.dtos.EmployeeDto;
import com.maksimkaxxl.springbootrestfulapi.dtos.responce.EmployeeSummaryDto;
import com.maksimkaxxl.springbootrestfulapi.dtos.responce.UploadedEmployeeResponse;
import com.maksimkaxxl.springbootrestfulapi.entities.Company;
import com.maksimkaxxl.springbootrestfulapi.entities.Employee;
import com.maksimkaxxl.springbootrestfulapi.exceptions.CompanyNotFoundException;
import com.maksimkaxxl.springbootrestfulapi.exceptions.EmployeeNotFoundException;
import com.maksimkaxxl.springbootrestfulapi.repository.CompanyRepository;
import com.maksimkaxxl.springbootrestfulapi.repository.EmployeeRepository;
import com.maksimkaxxl.springbootrestfulapi.services.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final ObjectMapper objectMapper;

    static {
        ObjectMapper mapper = new ObjectMapper();
        // Не створювати exception, якщо json має додаткові поля
        // без серіалізації. Це корисно, коли ви хочете використовувати pojo
        // для десеріалізації та дбає лише про частину json
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // Ігнорувати нульові значення під час запису json.
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public Employee createEmployee(EmployeeDto employeeDto) {
        Company existingCompany = getExistingCompany(employeeDto.company().name());
        Employee employee = mapEmployeeDtoToEntity(employeeDto, existingCompany);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with companyId: " + id));
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeDto employeeDto) {
        Employee existingEmployee = getExistingEmployee(id);
        Company existingCompany = getExistingCompany(employeeDto.company().name());
        mapEmployeeDtoToEntity(employeeDto, existingCompany, existingEmployee);
        return employeeRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee existingEmployee = getExistingEmployee(id);
        employeeRepository.delete(existingEmployee);
    }

    @Override
    public UploadedEmployeeResponse uploadEmployeesFromFile(MultipartFile file) {
        int successfulImports = 0;
        int failedImports = 0;
        List<String> failedImportsDetails = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             JsonParser parser = objectMapper.getFactory().createParser(inputStream)) {
            while (parser.nextToken() != null) {
                if (parser.currentToken() == JsonToken.START_OBJECT) {
                    EmployeeDto employeeDto = objectMapper.readValue(parser, EmployeeDto.class);

                    if (!employeeDto.position().matches("\\D+")) {
                        failedImportsDetails.add("Failed to import employee: " + employeeDto.name() + ". Reason: Position must not contain numbers.");
                        failedImports++;
                        continue;
                    }
                    try {
                        createEmployee(employeeDto);
                        successfulImports++;
                    } catch (CompanyNotFoundException e) {
                        failedImports++;
                        failedImportsDetails.add("Failed to import employee: " + employeeDto.name() + ". Reason: Company " + employeeDto.company().name() + " not found.");
                    }
                }
            }
        } catch (IOException e) {
            failedImportsDetails.add("Failed to process the file. Reason: " + e.getMessage());
            failedImports++;
        }

        return new UploadedEmployeeResponse(successfulImports, failedImports, failedImportsDetails);
    }


    @Override
    public Page<EmployeeSummaryDto> getEmployeesFromList(EmployeeSummaryDto employeeSummaryDto, Pageable pageable) {
        return Objects.nonNull(employeeSummaryDto) ?
                employeeRepository.findAllByNameOrPositionOrCompany(
                                employeeSummaryDto.name(),
                                employeeSummaryDto.age(),
                                employeeSummaryDto.position(),
                                pageable)
                        .orElseThrow(() -> new NoSuchElementException("hub1")) :
                employeeRepository.findAllEmployeeNameOrPositionOrCompany(pageable)
                        .orElseThrow(() -> new NoSuchElementException("hub2"));

    }

    @Override
    public Map<String, Object> getEmployeesByPage(EmployeeSummaryDto employeeSummaryDto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        Page<EmployeeSummaryDto> employeeSummaryDtos = Objects.nonNull(employeeSummaryDto) ?
                employeeRepository.findAllByNameOrPositionOrCompany(
                                employeeSummaryDto.name(),
                                employeeSummaryDto.age(),
                                employeeSummaryDto.position(),
                                pageable)
                        .orElseThrow(() -> new NoSuchElementException("hub1")) :
                employeeRepository.findAllEmployeeNameOrPositionOrCompany(pageable)
                        .orElseThrow(() -> new NoSuchElementException("hub2"));

        Map<String, Object> response = new HashMap<>();
        response.put("list", employeeSummaryDtos.getContent());
        response.put("totalPages", employeeSummaryDtos.getTotalPages());
        return response;
    }

    @Override
    public void generateEmployeeReport(HttpServletResponse response, EmployeeSummaryDto employeeSummaryDto)  {
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees-report.csv");

        try (var writer = response.getWriter()) {
            // Записуємо заголовок CSV файлу
            writer.println("Name,Age,Position");

            // Отримуємо всіх працівників за критеріями фільтрації
            List<EmployeeSummaryDto> employees = getFilteredEmployees(employeeSummaryDto);

            // Записуємо дані в CSV файл
            for (EmployeeSummaryDto employee : employees) {
                String csvLine = String.join(",", employee.name(), String.valueOf(employee.age()), employee.position());
                writer.println(csvLine);
            }
        } catch (IOException e) {
        }
    }

    private List<EmployeeSummaryDto> getFilteredEmployees(EmployeeSummaryDto employeeSummaryDto) {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by("id").descending());
        Page<EmployeeSummaryDto> employeesPage = employeeRepository.findAllByNameOrPositionOrCompany(
                employeeSummaryDto.name(),
                employeeSummaryDto.age(),
                employeeSummaryDto.position(),
                pageable
        ).orElseThrow(() -> new NoSuchElementException("Failed to get employees list"));

        return employeesPage.getContent();
    }

    private void createEmployeeCsv(EmployeeSummaryDto employeeSummaryDto, OutputStream outputStream) throws IOException {
        int pageNumber = 0;
        int pageSize = 20;
        Page<EmployeeSummaryDto> employeesPage;

        do {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            employeesPage = getEmployeesFromList(employeeSummaryDto, pageable);
            pageNumber++;

            for (EmployeeSummaryDto employee : employeesPage) {
                String csvLine = String.join(",", employee.name(), String.valueOf(employee.age()), employee.position()) + "\n";
                outputStream.write(csvLine.getBytes());
            }
        } while (employeesPage.hasNext());
    }



    private Company getExistingCompany(String companyName) {
        return companyRepository.findByName(companyName)
                .orElseThrow(() -> new CompanyNotFoundException("Company with name " + companyName + " not found"));
    }

    private Employee getExistingEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found"));
    }

    private Employee mapEmployeeDtoToEntity(EmployeeDto employeeDto, Company company) {
        Employee employee = new Employee();
        return mapEmployeeDtoToEntity(employeeDto, company, employee);
    }

    private Employee mapEmployeeDtoToEntity(EmployeeDto employeeDto, Company company, Employee employee) {
        employee.setName(employeeDto.name());
        employee.setAge(employeeDto.age());
        employee.setPosition(employeeDto.position());
        employee.setExperienceYears(employeeDto.experienceYears());
        employee.setInterests(employeeDto.interests());
        employee.setCompany(company);
        return employee;
    }

}
