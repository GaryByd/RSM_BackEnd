package com.rc.domain.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SnapShotList {
    @JsonProperty("snapshot_list")
    private Object snapshotListData;
    private Long total;
}
