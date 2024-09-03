package com.rc.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CheckedList {
    @JsonProperty("checklist_list")
    private List<?> checkedListData;
    private Long total;
}
