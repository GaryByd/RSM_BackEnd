package com.rc.controller;


import com.rc.domain.dto.ItemsDoneDTO;
import com.rc.domain.dto.Result;
import com.rc.service.IRsmPatrolItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 巡检项表 前端控制器
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@RestController
@RequestMapping("/api/mp/patrol")
public class RsmPatrolItemController {

    @Autowired
    private IRsmPatrolItemService patrolItemService;
    @ApiOperation(value = "根据巡查清单ID获取巡查列表")
    @RequestMapping("/checklists/{checklist_id}/items")
    public Result getPatrolItemByListId(
            @PathVariable("checklist_id") Integer checklistId
    )
    {
        return patrolItemService.getPatrolItemByListId(checklistId);
    }

    @ApiOperation(value = "巡查项完成")
    @RequestMapping("/checklists/{checklist_id}/items/{item_id}/done")
    public Result patrolItemDone(
            @PathVariable("checklist_id") Integer checklistId,
            @PathVariable("item_id") Integer itemId,
            @RequestBody ItemsDoneDTO itemsDoneDTO
    )
    {
        return patrolItemService.patrolItemDone(itemsDoneDTO,checklistId,itemId);
    }
}
