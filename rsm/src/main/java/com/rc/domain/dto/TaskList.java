package com.rc.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TaskList {
    @JsonProperty("task_list")
    private List<?> TaskListData;
    private Long total;
}
