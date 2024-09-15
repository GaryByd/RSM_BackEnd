package com.rc.controller;


import com.rc.domain.dto.Result;
import com.rc.service.IRsmRiskService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 风险库表 前端控制器
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@RestController
@RequestMapping("/api/mp")
public class RsmRiskController {
    @Autowired
    private IRsmRiskService rsmRiskService;


    @ApiOperation(value = "根据检查项ID获取风险")
    @RequestMapping("/patrol/checklists/{checklist_id}/items/{item_id}/risks/{risk_id}")
    public Result getRiskList(
            @PathVariable("checklist_id") Integer checklistId,
            @PathVariable("item_id") Integer itemId,
            @PathVariable("risk_id") Integer riskId
    )
    {
        return rsmRiskService.getRiskById(riskId);
    }


    @ApiOperation(value = "根据检查项ID获取风险")
    @RequestMapping("/risks/{risk_id}")
    public Result getRiskList(
            @PathVariable("risk_id") Integer riskId
    )
    {
        return rsmRiskService.getRiskById(riskId);
    }
}
