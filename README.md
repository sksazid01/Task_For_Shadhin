# Task Management REST API

A Spring Boot REST API that performs CRUD operations on tasks, backed by MySQL database using Spring JDBC (JdbcTemplate).

## 🛠️ Technology Stack

- **Java**: 21
- **Spring Boot**: 3.5.6
- **Database**: MySQL
- **Data Access**: Spring JDBC (JdbcTemplate) - No ORM/JPA/Hibernate
- **Build Tool**: Maven
- **Dependencies**: Spring Web, Spring JDBC, MySQL Connector, Lombok

## 📋 Project Structure

```
src/
├── main/
│   ├── java/com/shadhin/build_API/
│   │   ├── BuildApiApplication.java      # Main application class
│   │   ├── controller/
│   │   │   └── TaskController.java       # REST endpoints (@RestController)
│   │   ├── service/
│   │   │   └── TaskService.java          # Business logic (@Service)
│   │   ├── repository/
│   │   │   └── TaskRepository.java       # Data access layer (@Repository)
│   │   └── model/
│   │       └── Task.java                 # Task entity model
│   └── resources/
│       ├── application.yml               # Application configuration
│       ├── schema.sql                    # Database schema
│       └── data.sql                      # Sample data (optional)
```

## 🚀 Setup Instructions

### Prerequisites

1. **Java 17+** installed (Java 21 recommended)
2. **MySQL Server** installed and running
3. **Maven** (or use included Maven wrapper)

### Database Setup

1. Start MySQL server
2. Create database (auto-created by application):
   ```sql
   CREATE DATABASE shadhin;
   ```

3. Update database credentials in `src/main/resources/application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/shadhin?createDatabaseIfNotExist=true
       username: root
       password: Root@123
   ```

### Run the Application

#### Option 1: Using Maven Wrapper (Recommended)
```bash
# On Linux/Mac
./mvnw clean install
./mvnw spring-boot:run

# On Windows
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

#### Option 2: Using Maven
```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Alternative: Using H2 Database (Quick Run)

To use H2 instead of MySQL:

1. Add H2 dependency in `pom.xml`:
   ```xml
   <dependency>
       <groupId>com.h2database</groupId>
       <artifactId>h2</artifactId>
       <scope>runtime</scope>
   </dependency>
   ```

2. Update `application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:h2:mem:testdb
       driver-class-name: org.h2.Driver
       username: sa
       password:
   ```

## 📊 Database Schema

### Table: tasks

```sql
CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    priority INT NOT NULL CHECK (priority BETWEEN 1 AND 5),
    completed BOOLEAN DEFAULT FALSE
);
```

## 🔌 API Endpoints

### Base URL: `http://localhost:8080`

| Method | Endpoint | Description | Priority |
|--------|----------|-------------|----------|
| POST | `/tasks` | Create a new task | High |
| GET | `/tasks` | Get all tasks | High |
| GET | `/tasks/{id}` | Get task by ID | High |
| PUT | `/tasks/{id}/complete` | Mark task as completed | High |
| DELETE | `/tasks/{id}` | Delete task by ID | High |
| GET | `/tasks/highest-priority` | Get task with highest priority | Low |
| GET | `/tasks/stats` | Get task statistics | Low |

## 📝 Raw SQL Statements Used

### CRUD Operations

#### CREATE (INSERT)
```sql
INSERT INTO tasks (title, description, priority, completed) 
VALUES (?, ?, ?, ?)
```

#### READ (SELECT)
```sql
-- Get all tasks
SELECT * FROM tasks

-- Get task by ID
SELECT * FROM tasks WHERE id = ?
```

#### UPDATE
```sql
-- Update task
UPDATE tasks 
SET title = ?, description = ?, priority = ?, completed = ? 
WHERE id = ?

-- Mark as completed
UPDATE tasks SET completed = true WHERE id = ?
```

#### DELETE
```sql
DELETE FROM tasks WHERE id = ?
```

### Special Endpoints

#### Highest Priority Task
```sql
SELECT * FROM tasks ORDER BY priority DESC LIMIT 1
```

#### Task Statistics
```sql
-- Total tasks
SELECT COUNT(*) FROM tasks

-- Completed tasks
SELECT COUNT(*) FROM tasks WHERE completed = true

-- Pending tasks
SELECT COUNT(*) FROM tasks WHERE completed = false
```

## 🧪 Example API Requests & Responses

### 1. Create a Task
**Request:**
```http
POST http://localhost:8080/tasks
Content-Type: application/json

{
  "title": "Complete Spring Boot Project",
  "description": "Develop REST API with CRUD operations",
  "priority": 5,
  "completed": false
}
```

**Response:** (201 Created)
```json
{
  "id": 1,
  "title": "Complete Spring Boot Project",
  "description": "Develop REST API with CRUD operations",
  "priority": 5,
  "completed": false
}
```

### 2. Get All Tasks
**Request:**
```http
GET http://localhost:8080/tasks
```

**Response:** (200 OK)
```json
[
  {
    "id": 1,
    "title": "Complete Spring Boot Project",
    "description": "Develop REST API with CRUD operations",
    "priority": 5,
    "completed": false
  },
  {
    "id": 2,
    "title": "Write Documentation",
    "description": "Create comprehensive README",
    "priority": 4,
    "completed": false
  }
]
```

### 3. Get Task by ID
**Request:**
```http
GET http://localhost:8080/tasks/1
```

**Response:** (200 OK)
```json
{
  "id": 1,
  "title": "Complete Spring Boot Project",
  "description": "Develop REST API with CRUD operations",
  "priority": 5,
  "completed": false
}
```

### 4. Mark Task as Completed
**Request:**
```http
PUT http://localhost:8080/tasks/1/complete
```

**Response:** (200 OK)
```json
{
  "id": 1,
  "title": "Complete Spring Boot Project",
  "description": "Develop REST API with CRUD operations",
  "priority": 5,
  "completed": true
}
```

### 5. Delete Task
**Request:**
```http
DELETE http://localhost:8080/tasks/1
```

**Response:** (204 No Content)

### 6. Get Highest Priority Task
**Request:**
```http
GET http://localhost:8080/tasks/highest-priority
```

**Response:** (200 OK)
```json
{
  "id": 1,
  "title": "Complete Spring Boot Project",
  "description": "Develop REST API with CRUD operations",
  "priority": 5,
  "completed": false
}
```

### 7. Get Task Statistics
**Request:**
```http
GET http://localhost:8080/tasks/stats
```

**Response:** (200 OK)
```json
{
  "total": 5,
  "completed": 2,
  "pending": 3
}
```

## 🎯 Assumptions & Edge Cases Handled

### Assumptions:
1. **Priority Range**: Priority is constrained between 1-5 (validated in service layer and database constraint)
2. **Unique IDs**: Auto-generated IDs using MySQL AUTO_INCREMENT
3. **Default Values**: New tasks are marked as incomplete by default
4. **Required Fields**: Title and priority are mandatory fields

### Edge Cases Handled:

1. **Task Not Found**: Returns 404 Not Found when task ID doesn't exist
   ```json
   {
     "error": "Not Found",
     "message": "Task not found with id: 999"
   }
   ```

2. **Invalid Priority**: Throws IllegalArgumentException if priority is not between 1-5
   ```json
   {
     "error": "Priority must be between 1 and 5"
   }
   ```

3. **Empty Task List**: Returns empty array `[]` when no tasks exist

4. **No Highest Priority Task**: Returns error when getting highest priority from empty table

5. **Delete Non-existent Task**: Checks if task exists before deletion

6. **Database Connection Issues**: Gracefully handled by Spring's exception handling

## 🧪 Testing with Postman

1. Import the `Task_Management_API.postman_collection.json` file into Postman
2. The collection includes all 7 endpoints with example requests
3. Ensure the application is running on `http://localhost:8080`
4. Execute requests in order for best results

## 📂 Project Files

- **schema.sql**: Located at `src/main/resources/schema.sql` - Contains table creation script
- **data.sql**: Located at `src/main/resources/data.sql` - Contains sample data (optional)
- **Postman Collection**: `Task_Management_API.postman_collection.json` in root directory

## 🔍 Logging

SQL queries are logged in DEBUG mode. Check console output to see executed SQL statements.

## 📌 Key Features

✅ All CRUD operations using raw SQL via JdbcTemplate  
✅ No ORM/JPA/Hibernate for data fetching  
✅ Proper layered architecture (Controller → Service → Repository)  
✅ Constructor injection using @Autowired  
✅ Exception handling for edge cases  
✅ Input validation (priority range)  
✅ Database constraints (CHECK constraint on priority)  
✅ Comprehensive API documentation  
✅ Sample data for quick testing  

## 👨‍💻 Developer

**Shadhin**

## 📅 Submission Details

- **Development Time**: 2 Days (Max 8 Hours)
- **Submission Date**: 23/10/2025
- **Framework**: Spring Boot 3.5.6
- **Java Version**: 21

---

**Note**: Make sure MySQL is running before starting the application. The database and tables will be created automatically on startup.
