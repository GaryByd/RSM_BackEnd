package com.rc.domain.entity;

import com.alibaba.druid.support.http.util.IPAddress;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableField("user_name")
    private String username;

    /**
     * 主键
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long id;

    /**
     * 手机号码
     */
    @TableField("phonenumber")
    @JsonProperty("phone_number")
    private String phone;

    /**
     * 密码，加密存储
     */
    private String password;

    /**
     * 昵称，默认是随机字符
     */
    @TableField("nick_name")
    @JsonProperty("nickname")
    private String nickName;

    /**
     * 用户头像
     */
    @TableField("avatar")
    @JsonProperty("avatar")
    private String icon = "";

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @TableField("login_date")
    private LocalDateTime loginDate;

    @TableField("login_ip")
    private IPAddress loginIp;

    @TableField("open_id")
    private String openid;
}
