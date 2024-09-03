package com.rc.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginFormDTO {
    @JsonProperty("id")
    private Long userId;
    @JsonProperty("user_name")
    private String userName="";
    @JsonProperty("phone_number")
    private String phone="";
    @JsonProperty("password")
    private String passWord="";
    @JsonProperty("code")
    private String weiXinCode="";

//    @JsonProperty("verify_code")
//    private String code;
}
