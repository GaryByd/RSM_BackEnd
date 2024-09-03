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
 * 巡查清单表
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rsm_patrol_list")
@ApiModel(value="RsmPatrolList对象", description="巡查清单表")
public class RsmPatrolList implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "清单id")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private Long id;

    @ApiModelProperty(value = "巡查清单名称")
    private String patrolListName;

    @ApiModelProperty(value = "巡查清单描述")
    private String patrolListDesc;

    @ApiModelProperty(value = "分类 0-安全巡查 1-岗位巡查 2-位置")
    private Integer classify;

    @ApiModelProperty(value = "负责人id")
    private Long principalId;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "已检查数量")
    private Integer checkedCount;

    @ApiModelProperty(value = "发现隐患数量")
    private Integer hiddenTroubleCount;

    @ApiModelProperty(value = "完成时间")
    private LocalDateTime finishTime;

    @ApiModelProperty(value = "状态 0-未完成 1-已完成 2-已过期")
    private Integer status;

    @ApiModelProperty(value = "删除标志（0代表存在 1代表删除）")
    private Integer delFlag;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "备注信息")
    private String remark;


}
