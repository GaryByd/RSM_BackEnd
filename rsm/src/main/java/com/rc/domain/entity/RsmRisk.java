package com.rc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 风险库表
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rsm_risk")
@ApiModel(value="RsmRisk对象", description="风险库表")
public class RsmRisk implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "风险id")
    @TableId(value = "id", type = IdType.AUTO)
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "风险名称")
    @JsonProperty("risk_name")
    private String riskName;

    @ApiModelProperty(value = "风险类型")
    @JsonProperty("risk_type")
    private String riskType;

    @ApiModelProperty(value = "监管人员")
    @JsonProperty("supervisor")
    private String supervisor;

    @ApiModelProperty(value = "管控标准")
    @JsonProperty("control_standard")
    private String controlStandard;

//    @ApiModelProperty(value = "风险位置名")
//    @JsonProperty("position")
//    private String position;

    @ApiModelProperty(value = "风险等级")
    @JsonProperty("risk_level")
    private String riskLevel;

    @ApiModelProperty(value = "风险信息，作为设备风险则说明是什么设备，作为生产现场风险说明哪些生产现场，作为作业活动说明哪个作业")
    @JsonProperty("risk_info")
    private String riskInfo;

    @ApiModelProperty(value = "风险关联id，作为保留字段，如果没有设备表和生产现场表那么这个字段仅在风险类型为作业风险时有用，用于关联作业表")
    @JsonProperty("risk_relate_id")
    private Long riskRelateId;

    @ApiModelProperty(value = "审核人")
    @JsonProperty("auditor")
    private String auditor;

    @ApiModelProperty(value = "风险描述")
    @JsonProperty("risk_describe")
    private String riskDescribe;

    @ApiModelProperty(value = "风险后果")
    @JsonProperty("risk_conseq")
    private String riskConseq;

    @ApiModelProperty(value = "风险管理措施")
    @JsonProperty("control_measure")
    private String controlMeasure;

    @ApiModelProperty(value = "备注")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value = "创建人")
    @JsonIgnore
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改人")
    @JsonIgnore
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
