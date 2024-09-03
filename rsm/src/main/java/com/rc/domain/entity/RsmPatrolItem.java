package com.rc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 巡检项表
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("rsm_patrol_item")
@ApiModel(value="RsmPatrolItem对象", description="巡检项表")
public class RsmPatrolItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "巡检项id")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "巡查清单id")
    @JsonProperty("patrol_list_id")
    private Long patrolListId;

    @ApiModelProperty(value = "巡查点id")
    @JsonProperty("patrol_point_id")
    private Long patrolPointId;

    @ApiModelProperty(value = "风险待查库表id")
    @JsonProperty("unverified_risk_id")
    private Long unverifiedRiskId;

    @ApiModelProperty(value = "责任单位信息")
    @JsonProperty("responsible_organization")
    private String responsibleOrganization;

    @ApiModelProperty(value = "检测项")
    @JsonProperty("check_item")
    private String checkItem;

    @ApiModelProperty(value = "状态 0-未处理  2-未发现隐患 3-发现隐患")
    @JsonProperty("status")
    private Integer status;

    @ApiModelProperty(value = "备注信息")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value = "检查图片/隐患图片")
    @JsonProperty("handler_img")
    private String handlerImg;

    @ApiModelProperty(value = "检查描述/隐患描述")
    @JsonProperty("handler_desc")
    private String handlerDesc;


}
