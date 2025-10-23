package com.shadhin.build_API.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatsDTO {
    private long total;
    private long completed;
    private long pending;
}
