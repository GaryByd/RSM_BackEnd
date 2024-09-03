package com.rc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "巡查点名称")
    @JsonProperty("patrol_name")
    private String patrolName;

    @ApiModelProperty(value = "巡查点描述")
    @JsonProperty("patrol_desc")
    private String patrolDesc;

    @ApiModelProperty(value = "巡查点位置名")
    @JsonProperty("position")
    private String position;

    @ApiModelProperty(value = "具体位置描述")
    @JsonProperty("location_describe")
    private String locationDescribe;

    @ApiModelProperty(value = "安全员")
    @JsonProperty("security_officer")
    private String securityOfficer;

    @ApiModelProperty(value = "最近一次巡查时间")
    @JsonProperty("last_patrol_time")
    private LocalDateTime lastPatrolTime;

    @ApiModelProperty(value = "巡查次数")
    @JsonProperty("patrol_frequency")
    private Integer patrolFrequency;

    @ApiModelProperty(value = "状态 0-正常 1-停用")
    @JsonProperty("status")
    private Integer status;

    @ApiModelProperty(value = "删除标志（0代表存在 1代表删除）")
    @JsonProperty("del_flag")
    @JsonIgnore
    private Integer delFlag;

    @ApiModelProperty(value = "创建者")
    @JsonProperty("create_by")
    @JsonIgnore
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @JsonProperty("create_time")
    @JsonIgnore
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    @JsonProperty("update_by")
    @JsonIgnore
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @JsonProperty("update_time")
    @JsonIgnore
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注信息")
    @JsonProperty("remark")
    private String remark;


}
