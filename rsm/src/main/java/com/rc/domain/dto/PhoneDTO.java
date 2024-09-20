package com.rc.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class PhoneDTO {
    private String code;
    @JsonProperty("phone_number")
    private String phoneNumber;
}
