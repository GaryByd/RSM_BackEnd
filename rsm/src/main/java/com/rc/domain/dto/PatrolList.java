package com.rc.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class PatrolList {
    @JsonProperty("patrol_list")
    private List<?> PatrolListData;
    private Long total;
}
