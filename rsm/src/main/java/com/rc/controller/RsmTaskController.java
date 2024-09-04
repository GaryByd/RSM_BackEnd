package com.rc.controller;


import com.rc.domain.dto.Result;
import com.rc.domain.entity.RsmTask;
import com.rc.service.IRsmRiskService;
import com.rc.service.IRsmTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 作业表 前端控制器
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@Api("作业")
@RestController
@RequestMapping("/api/mp")
public class RsmTaskController {
    @Autowired
    private IRsmTaskService rsmTaskService;


    @ApiOperation("新增作业")
    @PostMapping("/task")
    public Result getTaskList(@RequestBody RsmTask rsmTask){
        return rsmTaskService.addTask(rsmTask);
    }


}
