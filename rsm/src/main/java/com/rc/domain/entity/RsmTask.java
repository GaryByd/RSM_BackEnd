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
 * 作业表
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rsm_task")
@ApiModel(value="RsmTask对象", description="作业表")
public class RsmTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "作业id")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "作业名称")
    @JsonProperty("task_name")
    private String taskName;

    @ApiModelProperty(value = "类型名称")
    @JsonProperty("type_name")
    private String typeName;

    @ApiModelProperty(value = "作业班组id")
    @JsonProperty("dept_id")
    private Long deptId;

    @ApiModelProperty(value = "班组名称")
    @JsonProperty("dept_name")
    private String deptName;

    @ApiModelProperty(value = "作业开始时间")
    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "作业结束时间")
    @JsonProperty("end_time")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "风险关联id，")
    @JsonProperty("risk_id")
    private Long riskId;

    @ApiModelProperty(value = "负责人姓名")
    @JsonProperty("mandate_holder")
    private String mandateHolder;

    @ApiModelProperty(value = "审核状态（1通过 0未审核 2未通过）")
    @JsonProperty("approval_status")
    private String approvalStatus;

    @ApiModelProperty(value = "审核人姓名")
    @JsonProperty("reviewer")
    private String reviewer;

    @ApiModelProperty(value = "作业描述")
    @JsonProperty("task_desc")
    private String taskDesc;

    @ApiModelProperty(value = "位置信息")
    @JsonProperty("position_info")
    private String positionInfo;

    @ApiModelProperty(value = "创建人")
    @JsonIgnore
    private String createBy;

    @ApiModelProperty(value = "备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @JsonIgnore
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改人")
    @JsonIgnore
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    @JsonIgnore
    private LocalDateTime updateTime;


}
