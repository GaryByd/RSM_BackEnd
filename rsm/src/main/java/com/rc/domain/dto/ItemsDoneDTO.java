package com.rc.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ItemsDoneDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "检查描述/隐患描述")
    @JsonProperty("handler_desc")
    private String handlerDesc;

    @ApiModelProperty(value = "检查图片/隐患图片")
    @JsonProperty("handler_img")
    private String handlerImg;

    @ApiModelProperty(value = "备注信息")
    @JsonProperty("remark")
    private String remark;

    @ApiModelProperty(value = "状态 0-未处理  2-未发现隐患 3-发现隐患")
    @JsonProperty("status")
    private String status;
}
