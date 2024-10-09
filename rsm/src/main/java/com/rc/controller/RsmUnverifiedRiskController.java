package com.rc.controller;


import com.rc.domain.dto.ItemsFormDTO;
import com.rc.domain.dto.Result;
import com.rc.service.IRsmUnverifiedRiskService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 风险待查项表 前端控制器
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@RestController
@RequestMapping("/api/mp/patrol")
public class RsmUnverifiedRiskController {
    @Autowired
    private IRsmUnverifiedRiskService rsmUnverifiedRiskService;
    @ApiOperation(value = "根据巡查清单ID获取巡查列表")
    @RequestMapping("/checklists/{checklist_id}/items")
    @PreAuthorize("hasAuthority('patrol:patrolRecord:look')")
    public Result getPatrolItemByListId(
            @PathVariable("checklist_id") Integer checklistId
    )
    {
        return rsmUnverifiedRiskService.getUnverifiedRiskByListId(checklistId);
    }

    @ApiOperation(value = "巡查项完成")
    @RequestMapping("/checklists/{checklist_id}/items/{item_id}/done")
    @PreAuthorize("hasAuthority('patrol:patrolRecord:look')")
    public Result patrolItemDone(
            @PathVariable("checklist_id") Integer checklistId,
            @PathVariable("item_id") Integer itemId,
            @RequestBody ItemsFormDTO itemsFormDTO
    )
    {
        return rsmUnverifiedRiskService.unverifiedRiskDone(itemsFormDTO,checklistId,itemId);
    }
}
