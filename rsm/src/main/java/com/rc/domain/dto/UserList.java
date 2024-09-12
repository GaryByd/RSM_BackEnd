package com.rc.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class UserList {
    //UserList数据
    @JsonProperty("list")
    List<?> userListData;
    //总数量
    Long total;
}
