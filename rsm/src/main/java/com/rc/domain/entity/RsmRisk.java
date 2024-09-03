package com.rc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private Long id;

    @ApiModelProperty(value = "风险名称")
    private String riskName;

    @ApiModelProperty(value = "风险类型")
    private String riskType;

    @ApiModelProperty(value = "监管人员")
    private String supervisor;

    @ApiModelProperty(value = "管控标准")
    private String controlStandard;

    @ApiModelProperty(value = "风险位置名")
    private String position;

    @ApiModelProperty(value = "风险等级")
    private String riskLevel;

    @ApiModelProperty(value = "风险信息，作为设备风险则说明是什么设备，作为生产现场风险说明哪些生产现场，作为作业活动说明哪个作业")
    private String riskInfo;

    @ApiModelProperty(value = "风险关联id，作为保留字段，如果没有设备表和生产现场表那么这个字段仅在风险类型为作业风险时有用，用于关联作业表")
    private Long riskRelateId;

    @ApiModelProperty(value = "审核人")
    private String auditor;

    @ApiModelProperty(value = "风险描述")
    private String riskDescribe;

    @ApiModelProperty(value = "风险后果")
    private String riskConseq;

    @ApiModelProperty(value = "风险管理措施")
    private String controlMeasure;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
