package com.shadhin.build_API.repository;

import com.shadhin.build_API.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper to map ResultSet to Task object
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

    // Create a new task
    public Task save(Task task) {
        String sql = "INSERT INTO tasks (title, description, priority, completed) VALUES (?, ?, ?, ?)";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setInt(3, task.getPriority());
            ps.setBoolean(4, task.isCompleted());
            return ps;
        }, keyHolder);
        
        task.setId(keyHolder.getKey().intValue());
        return task;
    }

    // Find all tasks
    public List<Task> findAll() {
        String sql = "SELECT * FROM tasks";
        return jdbcTemplate.query(sql, taskRowMapper);
    }

    // Find task by ID
    public Optional<Task> findById(int id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        List<Task> tasks = jdbcTemplate.query(sql, taskRowMapper, id);
        return tasks.isEmpty() ? Optional.empty() : Optional.of(tasks.get(0));
    }

    // Update task
    public int update(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, priority = ?, completed = ? WHERE id = ?";
        return jdbcTemplate.update(sql, task.getTitle(), task.getDescription(), 
                                   task.getPriority(), task.isCompleted(), task.getId());
    }

    // Delete task by ID
    public int deleteById(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Mark task as completed
    public int markAsCompleted(int id) {
        String sql = "UPDATE tasks SET completed = true WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Find task with highest priority
    public Optional<Task> findTopByOrderByPriorityDesc() {
        String sql = "SELECT * FROM tasks ORDER BY priority DESC LIMIT 1";
        List<Task> tasks = jdbcTemplate.query(sql, taskRowMapper);
        return tasks.isEmpty() ? Optional.empty() : Optional.of(tasks.get(0));
    }

    // Count total tasks
    public long count() {
        String sql = "SELECT COUNT(*) FROM tasks";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0L;
    }

    // Count completed tasks
    public long countCompletedTasks() {
        String sql = "SELECT COUNT(*) FROM tasks WHERE completed = true";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0L;
    }

    // Count pending tasks
    public long countPendingTasks() {
        String sql = "SELECT COUNT(*) FROM tasks WHERE completed = false";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0L;
    }
}