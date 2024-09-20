package com.rc.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PassWordDTO {
    @JsonProperty("old")
    private String oldPassword;
    @JsonProperty("new")
    private String newPassword;
}
