package com.rc.domain.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String nickname="";
    private String avatar="";
    private String phone_number="";
    private String token="";
}
