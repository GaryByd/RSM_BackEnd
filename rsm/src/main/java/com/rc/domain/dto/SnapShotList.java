package com.rc.domain.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SnapShotList {
    @JsonProperty("snapshot_list")
    private List<?> snapshotListData;
    private Long total;
}
