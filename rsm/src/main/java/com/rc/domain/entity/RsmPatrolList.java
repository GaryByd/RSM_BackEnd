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
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "巡查清单名称")
    @JsonProperty("patrol_list_name")
    private String patrolListName;

    @ApiModelProperty(value = "巡查清单描述")
    @JsonProperty("patrol_list_desc")
    private String patrolListDesc;

    @ApiModelProperty(value = "分类 0-安全巡查 1-岗位巡查 2-位置")
    @JsonProperty("classify")
    private Integer classify;

    @ApiModelProperty(value = "负责人id")
    @JsonProperty("principal_id")
    private Long principalId;

    @ApiModelProperty(value = "开始时间")
    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonProperty("end_time")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "已检查数量")
    @JsonProperty("checked_count")
    private Integer checkedCount;

    @ApiModelProperty(value = "发现隐患数量")
    @JsonProperty("hidden_trouble_count")
    private Integer hiddenTroubleCount;

    @ApiModelProperty(value = "完成时间")
    @JsonProperty("finish_time")
    private LocalDateTime finishTime;

    @ApiModelProperty(value = "状态 0-未完成 1-已完成 2-已过期")
    @JsonProperty("status")
    private Integer status;

    @ApiModelProperty(value = "删除标志（0代表存在 1代表删除）")
    @JsonProperty("del_flag")
    private Integer delFlag;

    @JsonIgnore
    @ApiModelProperty(value = "创建者")
    @JsonProperty("create_by")
    private String createBy;

    @JsonIgnore
    @ApiModelProperty(value = "创建时间")
    @JsonProperty("create_time")
    private LocalDateTime createTime;

    @JsonIgnore
    @ApiModelProperty(value = "更新时间")
    @JsonProperty("update_time")
    private LocalDateTime updateTime;

    @JsonIgnore
    @ApiModelProperty(value = "修改者")
    @JsonProperty("update_by")
    private String updateBy;

    @ApiModelProperty(value = "备注信息")
    @JsonProperty("remark")
    private String remark;
}
