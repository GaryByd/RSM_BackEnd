package com.rc.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static UserDTO parse(String str) {
        // 定义正则表达式
        String regex = "UserDTO\\(userId=(.*?), nickName=(.*?), avatar=(.*?), phoneNumber=(.*?), remark=(.*?)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            // 提取字段值
            String userIdStr = matcher.group(1);
            String nickName = matcher.group(2);
            String avatar = matcher.group(3);
            String phoneNumber = matcher.group(4);
            String remark = matcher.group(5);

            // 创建 UserDTO 实例
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(userIdStr.equals("null") ? null : Long.parseLong(userIdStr.trim()));
            userDTO.setNickName(nickName.trim());
            userDTO.setAvatar(avatar.trim());
            userDTO.setPhoneNumber(phoneNumber.trim());
            userDTO.setRemark(remark.trim());

            return userDTO;
        }

        throw new IllegalArgumentException("Invalid input string");
    }
}
