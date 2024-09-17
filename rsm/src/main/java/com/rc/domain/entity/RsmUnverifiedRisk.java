package com.rc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 风险待查项表
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rsm_unverified_risk")
@ApiModel(value="RsmUnverifiedRisk对象", description="风险待查项表")
public class RsmUnverifiedRisk implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "待查项id")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private Long id;

    @ApiModelProperty(value = "待查项名称")
    @JsonProperty("name")
    private String unverifiedRiskName;

    @ApiModelProperty(value = "关联风险id")
    @JsonProperty("risk_id")
    private Long riskId;

    @ApiModelProperty(value = "清单id")
    @JsonProperty("patrol_list_id")
    private Long patrolListId;

    @ApiModelProperty(value = "类型")
    @JsonProperty("risk_type")
    private String riskType;

    @ApiModelProperty(value = "管理员")
    @JsonProperty("admin")
    private String admin;

    @ApiModelProperty(value = "巡查标准")
    @JsonProperty("control_standard")
    private String controlStandard;

    @ApiModelProperty(value = "巡查点id")
    @JsonProperty("patrol_point_id")
    private Integer position;

    @ApiModelProperty(value = "状态")
    @JsonProperty("status")
    private int status;

    @ApiModelProperty(value = "备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value = "创建人")
    @JsonProperty("create_by")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改人")
    @JsonProperty("update_by")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @JsonProperty("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;




}
