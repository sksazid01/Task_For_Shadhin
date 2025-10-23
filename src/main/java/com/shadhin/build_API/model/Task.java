package com.shadhin.build_API.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private int id;
    private String title;
    private String description;
    private int priority; // 1-5
    private boolean completed;
}