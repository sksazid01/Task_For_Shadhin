# SQL Statements Documentation

This document contains all raw SQL statements used in the Task Management REST API.

## Table Creation

### Schema Definition (schema.sql)
```sql
DROP TABLE IF EXISTS tasks;

CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    priority INT NOT NULL CHECK (priority BETWEEN 1 AND 5),
    completed BOOLEAN DEFAULT FALSE
);
```

## CRUD Operations

### CREATE Operations

#### Insert New Task
```sql
INSERT INTO tasks (title, description, priority, completed) 
VALUES (?, ?, ?, ?)
```
- **Used in**: `TaskRepository.save()`
- **Parameters**: 
  1. title (String)
  2. description (String)
  3. priority (Integer, 1-5)
  4. completed (Boolean)
- **Returns**: Generated task ID via `KeyHolder`

---

### READ Operations

#### Select All Tasks
```sql
SELECT * FROM tasks
```
- **Used in**: `TaskRepository.findAll()`
- **Returns**: List of all tasks

#### Select Task by ID
```sql
SELECT * FROM tasks WHERE id = ?
```
- **Used in**: `TaskRepository.findById()`
- **Parameters**: 
  1. id (Integer)
- **Returns**: Single task or empty Optional

#### Select Highest Priority Task
```sql
SELECT * FROM tasks ORDER BY priority DESC LIMIT 1
```
- **Used in**: `TaskRepository.findTopByOrderByPriorityDesc()`
- **Returns**: Task with highest priority value
- **Note**: Returns first task if multiple tasks have same highest priority

---

### UPDATE Operations

#### Update Complete Task
```sql
UPDATE tasks 
SET title = ?, description = ?, priority = ?, completed = ? 
WHERE id = ?
```
- **Used in**: `TaskRepository.update()`
- **Parameters**: 
  1. title (String)
  2. description (String)
  3. priority (Integer)
  4. completed (Boolean)
  5. id (Integer)
- **Returns**: Number of rows affected

#### Mark Task as Completed
```sql
UPDATE tasks SET completed = true WHERE id = ?
```
- **Used in**: `TaskRepository.markAsCompleted()`
- **Parameters**: 
  1. id (Integer)
- **Returns**: Number of rows affected (1 if successful)

---

### DELETE Operations

#### Delete Task by ID
```sql
DELETE FROM tasks WHERE id = ?
```
- **Used in**: `TaskRepository.deleteById()`
- **Parameters**: 
  1. id (Integer)
- **Returns**: Number of rows deleted

---

## Statistics Queries

### Count Total Tasks
```sql
SELECT COUNT(*) FROM tasks
```
- **Used in**: `TaskRepository.count()`
- **Returns**: Total number of tasks (Long)

### Count Completed Tasks
```sql
SELECT COUNT(*) FROM tasks WHERE completed = true
```
- **Used in**: `TaskRepository.countCompletedTasks()`
- **Returns**: Number of completed tasks (Long)

### Count Pending Tasks
```sql
SELECT COUNT(*) FROM tasks WHERE completed = false
```
- **Used in**: `TaskRepository.countPendingTasks()`
- **Returns**: Number of pending/incomplete tasks (Long)

---

## Sample Data (data.sql)

```sql
-- Sample data for testing (optional)
INSERT INTO tasks (title, description, priority, completed) VALUES
('Complete Spring Boot Project', 'Develop REST API with CRUD operations using JdbcTemplate', 5, false),
('Write Documentation', 'Create comprehensive README.md with setup instructions', 4, false),
('Test API Endpoints', 'Test all endpoints using Postman', 3, false),
('Review Code', 'Code review and refactoring', 2, true),
('Deploy Application', 'Deploy to production environment', 1, false);
```

---

## SQL Execution Details

### JdbcTemplate Methods Used

1. **`update()`** - For INSERT, UPDATE, DELETE operations
2. **`query()`** - For SELECT operations returning multiple rows
3. **`queryForObject()`** - For SELECT operations returning single value (e.g., COUNT)
4. **`PreparedStatement`** - For parameterized queries to prevent SQL injection

### RowMapper Implementation

```java
private final RowMapper<Task> taskRowMapper = new RowMapper<Task>() {
    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        task.setPriority(rs.getInt("priority"));
        task.setCompleted(rs.getBoolean("completed"));
        return task;
    }
};
```

---

## Database Constraints

1. **Primary Key**: `id` column with AUTO_INCREMENT
2. **NOT NULL**: `title` and `priority` fields are mandatory
3. **CHECK Constraint**: Priority must be between 1 and 5
4. **DEFAULT**: `completed` defaults to `false` for new tasks

---

## Performance Considerations

1. **Indexing**: Primary key on `id` provides fast lookups
2. **LIMIT Clause**: Used in highest-priority query to return single result
3. **Prepared Statements**: All queries use parameterized statements for security and performance
4. **Connection Pooling**: Managed by Spring Boot's HikariCP (default)

---

## Security Features

✅ **SQL Injection Prevention**: All queries use PreparedStatement with parameters  
✅ **Input Validation**: Priority range validated in service layer  
✅ **Database Constraints**: CHECK constraint on priority at database level  
✅ **No Dynamic SQL**: All queries are static with placeholders  

---

## Query Logging

SQL queries can be logged by setting in `application.yml`:
```yaml
logging:
  level:
    org.springframework.jdbc.core: DEBUG
```

This will print all executed SQL statements to the console for debugging purposes.
