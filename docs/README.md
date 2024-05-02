# Spring Boot RESTful API

This project implements a RESTful API using Spring Boot, allowing for the management of companies and employees. It provides endpoints for creating, updating, deleting, and retrieving companies and employees, as well as uploading employee data from a file and generating reports.

## Usage

1. **Clone the Repository**: Clone this repository to your local machine.
2. **Set Up the Database**: Configure the database settings in `application.properties`.
3. **Build and Run**: Build and run the application using Maven or your preferred IDE.
4. **Access the API**: Use tools like Postman or cURL to interact with the API endpoints.

## Features

- **Company Management**: Create, update, delete, and retrieve companies.
- **Employee Management**: Create, update, delete, and retrieve employees.
- **File Upload**: Upload employee data from a file CSV.
- **Report Generation**: Generate reports in CSV format based on specified criteria.

## Technologies Used

- **Spring Boot**: Framework for building Java applications.
- **Spring Data JPA**: Simplifies the implementation of data access layers.
- **Spring Web**: Provides basic web support, including RESTful APIs.
- **Jackson**: JSON (de)serialization library for Java.
- **Lombok**: Library for reducing boilerplate code in Java classes.

## API Endpoints
### Company Endpoints

- **Retrieve All Companies**: `GET /api/company`

  ![img_1.png](img/img_1.png)

- **Create a Company**: `POST /api/company`

  ![img.png](img/img.png)

- **Update a Company**: `PUT /api/company/{id}`

  ![img_3.png](img/img_3.png)

- **Delete a Company**: `DELETE /api/company/{id}`

  ![img_2.png](img/img_2.png)

### Employee Endpoints

- **Create an Employee**: `POST /api/employee`

  ![img_4.png](img/img_4.png)

- **Retrieve a Specific Employee**: `GET /api/employee/{id}`

  ![img_5.png](img/img_5.png)

- **Update an Employee**: `PUT /api/employee/{id}`

  ![img_6.png](img/img_6.png)

- **Delete an Employee**: `DELETE /api/employee/{id}`

  ![img_7.png](img/img_7.png)

- **Upload Employee Data from a File**: `POST /api/employee/upload`

  ![img_8.png](img/img_8.png)

- **Generate Employee Report**: `POST /api/employee/_list`

  ![img_10.png](img/img_10.png)

- **Generate Employee Report at CSV file**: `POST /api/employee/_report`

  ![img_9.png](img/img_9.png)

---

### License
This project is licensed under the MIT License. See the [LICENSE](LICENSE.md) file for details.

----

**Developed by Maksym Chalyi in 2024.**