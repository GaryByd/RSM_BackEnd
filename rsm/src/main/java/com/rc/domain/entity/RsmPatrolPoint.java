package com.rc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 巡查点表
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rsm_patrol_point")
@ApiModel(value="RsmPatrolPoint对象", description="巡查点表")
public class RsmPatrolPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "巡查点id")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private Long id;

    @ApiModelProperty(value = "巡查点名称")
    private String patrolName;

    @ApiModelProperty(value = "巡查点描述")
    private String patrolDesc;

    @ApiModelProperty(value = "巡查点位置名")
    private String position;

    @ApiModelProperty(value = "具体位置描述")
    private String locationDescribe;

    @ApiModelProperty(value = "安全员")
    private String securityOfficer;

    @ApiModelProperty(value = "最近一次巡查时间")
    private LocalDateTime lastPatrolTime;

    @ApiModelProperty(value = "巡查次数")
    private Integer patrolFrequency;

    @ApiModelProperty(value = "状态 0-正常 1-停用")
    private Integer status;

    @JsonIgnore
    @ApiModelProperty(value = "删除标志（0代表存在 1代表删除）")
    private Integer delFlag;

    @JsonIgnore
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @JsonIgnore
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @JsonIgnore
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @JsonIgnore
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注信息")
    private String remark;


}
