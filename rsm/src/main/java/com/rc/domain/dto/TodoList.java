package com.rc.domain.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TodoList {
    @JsonProperty("total")
    private Long total;
    @JsonProperty("patrol")
    private Long patrol;
    @JsonProperty("hidden_handle")
    private Long hiddenHandle;
    @JsonProperty("snapshot_approval")
    private Long snapshotApproval;
    @JsonProperty("task_approval")
    private Long taskApproval;
}
