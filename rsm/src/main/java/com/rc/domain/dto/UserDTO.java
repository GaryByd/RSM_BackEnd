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
        // 如果输入字符串为空或为 null，直接返回 null 或者空的 UserDTO 对象
        if (str == null || str.trim().isEmpty()) {
            // 根据业务需求，选择返回 null 或一个空的 UserDTO 对象
            return null;  // 或者返回 new UserDTO();
        }

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

            // 判断 userId 字段是否为 null 或空字符串
            if (userIdStr != null && !userIdStr.trim().isEmpty() && !"null".equals(userIdStr.trim())) {
                userDTO.setUserId(Long.parseLong(userIdStr.trim()));
            } else {
                userDTO.setUserId(null);
            }

            // 设置其他字段，判断为空时处理
            userDTO.setNickName(nickName != null && !nickName.trim().isEmpty() ? nickName.trim() : null);
            userDTO.setAvatar(avatar != null && !avatar.trim().isEmpty() ? avatar.trim() : null);
            userDTO.setPhoneNumber(phoneNumber != null && !phoneNumber.trim().isEmpty() ? phoneNumber.trim() : null);
            userDTO.setRemark(remark != null && !remark.trim().isEmpty() ? remark.trim() : null);

            return userDTO;
        }
        throw new IllegalArgumentException("Invalid input string");
    }
}
