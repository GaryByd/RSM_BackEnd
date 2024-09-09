package com.rc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 隐患表
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rsm_hidden_trouble")
@ApiModel(value="RsmHiddenTrouble对象", description="隐患表")
public class RsmHiddenTrouble implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "隐患id")
    @JsonProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "隐患标题")
    @JsonProperty("trouble_title")
    private String troubleTitle;

    @ApiModelProperty(value = "创建人id")
    @JsonProperty("creator_id")
    private Long creatorId;


    @ApiModelProperty(value = "隐患图片路径-逗号分隔")
    @JsonProperty("trouble_img_path")
    private String troubleImgPath;

    @ApiModelProperty(value = "隐患描述")
    @JsonProperty("trouble_desc")
    private String troubleDesc;

    @ApiModelProperty(value = "处理状态0-未处理,1-已处理")
    @JsonProperty("status")
    private Integer status;

    @ApiModelProperty(value = "隐患分类 0-其他")
    @JsonProperty("trouble_classify")
    private Integer troubleClassify;

    @ApiModelProperty(value = "隐患来源 0-巡查 1-随手拍 2-上传")
    @JsonProperty("source")
    private Integer source;

    @ApiModelProperty(value = "处理人id")
    @JsonProperty("handler_id")
    private Long handlerId;

    @ApiModelProperty(value = "处理图片路径")
    @JsonProperty("handle_img_path")
    private String handleImgPath;

    @ApiModelProperty(value = "整改描述")
    @JsonProperty("rectify_desc")
    private String rectifyDesc;

    @ApiModelProperty(value = "整改时间")
    @JsonProperty("rectify_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rectifyTime;

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
