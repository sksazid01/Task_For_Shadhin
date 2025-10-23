package com.shadhin.build_API.service;

import com.shadhin.build_API.model.Task;
import com.shadhin.build_API.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepository repo;

    public Task createTask(Task task) {
        // Validate priority is between 1-5
        if (task.getPriority() < 1 || task.getPriority() > 5) {
            throw new IllegalArgumentException("Priority must be between 1 and 5");
        }
        return repo.save(task);
    }

    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    public Task getTaskById(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    public void deleteTask(int id) {
        // Check if task exists before deletion
        getTaskById(id);
        repo.deleteById(id);
    }

    public Task completeTask(int id) {
        Task task = getTaskById(id);
        repo.markAsCompleted(id);
        task.setCompleted(true);
        return task;
    }

    public Task getHighestPriority() {
        return repo.findTopByOrderByPriorityDesc()
                .orElseThrow(() -> new RuntimeException("No tasks found"));
    }

    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", repo.count());
        stats.put("completed", repo.countCompletedTasks());
        stats.put("pending", repo.countPendingTasks());
        return stats;
    }
}