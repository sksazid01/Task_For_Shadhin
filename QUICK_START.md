# Quick Start Guide

## Prerequisites Check

Before running the application, ensure you have:

- [ ] Java 17 or higher installed
- [ ] MySQL Server installed and running
- [ ] Maven installed (or use included wrapper)
- [ ] Git installed (for version control)

## Setup Steps

### 1. Database Configuration

Start MySQL and update credentials if needed in `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/shadhin?createDatabaseIfNotExist=true
    username: root
    password: Root@123  # Change this to your MySQL password
```

### 2. Build the Project

```bash
./mvnw clean install
```

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Verify Installation

Open a new terminal and run:

```bash
curl http://localhost:8080/tasks
```

You should see an empty array `[]` or sample data if `data.sql` was loaded.

## Quick API Test

### Create a Task
```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "My First Task",
    "description": "Testing the API",
    "priority": 5,
    "completed": false
  }'
```

### Get All Tasks
```bash
curl http://localhost:8080/tasks
```

### Get Task Statistics
```bash
curl http://localhost:8080/tasks/stats
```

## Using Postman

1. Import `Task_Management_API.postman_collection.json`
2. Ensure application is running
3. Execute requests in the collection

## Using Test Script

Run the automated test script:

```bash
./test-api.sh
```

This will test all endpoints automatically.

## Troubleshooting

### MySQL Connection Error
- Verify MySQL is running: `sudo systemctl status mysql`
- Check credentials in `application.yml`
- Ensure database `shadhin` exists or will be auto-created

### Port 8080 Already in Use
Change port in `application.yml`:
```yaml
server:
  port: 8081
```

### Build Errors
```bash
./mvnw clean
./mvnw clean install -U
```

## Project Structure

```
Task_For_Shadhin/
├── src/main/java/com/shadhin/build_API/
│   ├── controller/      # REST endpoints
│   ├── service/         # Business logic
│   ├── repository/      # Data access (JdbcTemplate)
│   ├── model/           # Task entity
│   ├── dto/             # Data transfer objects
│   └── exception/       # Exception handlers
├── src/main/resources/
│   ├── application.yml  # Configuration
│   ├── schema.sql       # Database schema
│   └── data.sql         # Sample data
├── README.md            # Full documentation
├── SQL_STATEMENTS.md    # SQL queries documentation
└── Task_Management_API.postman_collection.json
```

## Next Steps

1. ✅ Review the code in each layer
2. ✅ Test all endpoints using Postman
3. ✅ Read the SQL_STATEMENTS.md for query details
4. ✅ Customize as needed for your requirements

---

**Happy Coding! 🚀**
