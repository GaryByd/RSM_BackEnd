package com.rc.controller;


import com.rc.domain.dto.Result;
import com.rc.service.IRsmPatrolListService;
import com.rc.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * <p>
 * 巡查清单表 前端控制器
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@RestController
@RequestMapping("/api/mp/patrol")
@Api(tags = "巡查清单")
public class RsmPatrolListController {
    @Resource
    private IRsmPatrolListService rsmPatrolListService;

    //分页查询
    @ApiOperation(value = "获取巡查清单")
    @RequestMapping("/checklists")
    public Result getPatrolList(
            @RequestParam("page_number") Integer pageNumber,
            @RequestParam("page_size") Integer pageSize,
            @RequestParam(value = "start_time",required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String startTime,
            @RequestParam(value = "end_time",required = false)@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String endTime,
            @RequestParam(value = "status",required = false) Integer status
    ){
        return rsmPatrolListService.getPatrolList(pageNumber, pageSize, startTime, endTime, status);
    }
}
