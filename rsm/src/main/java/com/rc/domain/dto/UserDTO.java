package com.rc.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @JsonProperty("id")
    private Long userId;
    @JsonProperty("nickname")
    private String nickName;
    private String avatar="";
    @JsonProperty("phone_number")
    private String phoneNumber="";
    @JsonProperty("role")
    private String remark="";
}
