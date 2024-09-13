package com.rc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 隐患处理记录表
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rsm_hidden_trouble_record")
@ApiModel(value="RsmHiddenTroubleRecord对象", description="隐患处理记录表")
public class RsmHiddenTroubleRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "隐患id")
    private Long troubleId;

    @ApiModelProperty(value = "隐患标题")
    private String troubleTitle;

    @ApiModelProperty(value = "创建人id")
    private Long creatorId;

    @ApiModelProperty(value = "创建人联系方式")
    private String phone;

    @ApiModelProperty(value = "隐患图片路径-逗号分隔")
    private String troubleImgPath;

    @ApiModelProperty(value = "隐患描述")
    private String troubleDesc;

    @ApiModelProperty(value = "处理人id")
    private Long handlerId;

    @ApiModelProperty(value = "处理图片路径")
    private String handleImgPath;

    @ApiModelProperty(value = "整改描述")
    private String rectifyDesc;

    @ApiModelProperty(value = "整改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime rectifyTime;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;


}
