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
    private String unverifiedRiskName;

    @ApiModelProperty(value = "关联风险id")
    private Long riskId;

    @ApiModelProperty(value = "类型")
    private String riskType;

    @ApiModelProperty(value = "管理员")
    private String admin;

    @ApiModelProperty(value = "巡查标准")
    private String controlStandard;

    @ApiModelProperty(value = "作业位置名")
    private String position;

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
