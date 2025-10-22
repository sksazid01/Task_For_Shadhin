package com.shadhin.build_API.service;


import com.shadhin.build_API.entity.Task;
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
        return repo.save(task);
    }

    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    public Task getTaskById(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public void deleteTask(int id) {
        repo.deleteById(id);
    }

    public Task completeTask(int id) {
        Task task = getTaskById(id);
        task.setCompleted(true);
        return repo.save(task);
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