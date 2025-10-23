package com.shadhin.build_API.controller;

import com.shadhin.build_API.entity.Task;
import com.shadhin.build_API.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody Task task) {
        return new ResponseEntity<>(service.createTask(task), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Task> getAll() {
        return service.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getById(@PathVariable int id) {
        return service.getTaskById(id);
    }

    @PutMapping("/{id}/complete")
    public Task markAsComplete(@PathVariable int id) {
        return service.completeTask(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/highest-priority")
    public Task getHighestPriorityTask() {
        return service.getHighestPriority();
    }

    @GetMapping("/stats")
    public Map<String, Long> getTaskStats() {
        return service.getStats();
    }
}