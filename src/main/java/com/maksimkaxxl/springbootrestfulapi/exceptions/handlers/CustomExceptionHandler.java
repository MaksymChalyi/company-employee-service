package com.maksimkaxxl.springbootrestfulapi.exceptions.handlers;

import com.maksimkaxxl.springbootrestfulapi.exceptions.CompanyNotFoundException;
import com.maksimkaxxl.springbootrestfulapi.exceptions.DuplicateCompanyNameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateCompanyNameException.class)
    public final ResponseEntity<Object> handleDuplicateCompanyNameException(DuplicateCompanyNameException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CompanyNotFoundException.class)
    public final ResponseEntity<Object> handleCompanyNotFoundException(CompanyNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

}