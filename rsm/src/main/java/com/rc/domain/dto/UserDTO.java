package com.rc.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    @JsonProperty("nickname")
    private String nickName;
    private String avatar="";
    @JsonProperty("phone_number")
    private String phone_number="";
    @JsonProperty("role")
    private String remark="";
}
