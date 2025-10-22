package com.shadhin.build_API.repository;

import com.shadhin.build_API.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Optional<Task> findTopByOrderByPriorityDesc();

    @Query("SELECT COUNT(t) FROM Task t WHERE t.completed = true")
    long countCompletedTasks();

    @Query("SELECT COUNT(t) FROM Task t WHERE t.completed = false")
    long countPendingTasks();
}