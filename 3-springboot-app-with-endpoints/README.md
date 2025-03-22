# Backend Internship - Spring Boot Application

This is a basic Spring Boot application setup using the Maven build tool.


[//]: # (## Project Name)

**backendinternship**

## Requirements

- Java 17 or later
- Maven 3.6+
- IntelliJ IDEA

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/solayof/Flexisaf-Internship-Program.git
cd backendinternship
```

### 2. Run the application

```bash
./mnvn clean package
```
 ```bash
 java -jar target/*.jar
 ```

### 3. Access the application
```bash
curl --request l -sL \
     --url 'http://localhost:8080'
```

# Task
```
Create a Spring BootApplication with atleast 4 endpoints
```
# Routes


| Endpoint                           | description                               |
|:----------------------------------:|:-----------------------------------------:|
| /docs.html                         | endpoints documentation                    |
| /teachers                          | list of teachers and create a teacher                          |
|/teachers/{id}                      | get and update a specific teacher         |
| /staff                          | list of staff and create  staff                          |
|/staff/{id}                      | get and update a specific staff         |
