package com.rc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 随手拍问题表
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rsm_snapshot")
@ApiModel(value="RsmSnapshot对象", description="随手拍问题表")
public class RsmSnapshot implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "随手拍id")
    @TableId(value = "id", type = IdType.AUTO)
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "问题标题")
    @JsonProperty("question_title")
    private String questionTitle;

    @ApiModelProperty(value = "问题描述")
    @JsonProperty("question_desc")
    private String questionDesc;

    @ApiModelProperty(value = "上传人用户名")
    @JsonProperty("creator_name")
    private String creatorName;

    @ApiModelProperty(value = "上传人id")
    @JsonProperty("creator_id")
    private Long creatorId;

    @ApiModelProperty(value = "联系方式")
    @JsonProperty("phone")
    private String phone;

    @ApiModelProperty(value = "图片路径")
    @JsonProperty("img_path")
    private String imgPath;

    @ApiModelProperty(value = "处理状态 0-未处理 1-处理完成")
    @JsonProperty("status")
    private Integer status;

    @ApiModelProperty(value = "处理人")
    @JsonProperty("handler_id")
    private Long handlerId;

    @ApiModelProperty(value = "处理完成的现场的图片路径")
    @JsonProperty("handler_img_path")
    private String handlerImgPath;

    @ApiModelProperty(value = "审批回复")
    @JsonProperty("approval_reply")
    private String approvalReply;

    @ApiModelProperty(value = "事件定性 0-隐患 1-非隐患")
    @JsonProperty("property")
    private Integer property;

    @ApiModelProperty(value = "处理时间")
    @JsonProperty("handler_time")
    private LocalDateTime handlerTime;

    @ApiModelProperty(value = "创建时间")
    @JsonProperty("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @JsonProperty("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    @JsonProperty("remark")
    private String remark;


}
