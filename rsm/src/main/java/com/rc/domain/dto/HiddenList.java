package com.rc.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HiddenList {
    @JsonProperty("hidden_list")
    private List<?> hiddenListData;
    private Long total;
}
