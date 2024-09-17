package com.rc.controller;


import com.rc.domain.dto.Result;
import com.rc.domain.entity.RsmTask;
import com.rc.service.IRsmRiskService;
import com.rc.service.IRsmTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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
    public Result getTaskList(@RequestBody RsmTask rsmTask) {
        return rsmTaskService.addTask(rsmTask);
    }

    @ApiOperation("查询作业列表")
    @GetMapping("/tasks")
    public Result getTaskList(
            @RequestParam("page_number") Integer pageNumber,
            @RequestParam("page_size") Integer pageSize,
            @RequestParam(value = "start_time", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String startTime,
            @RequestParam(value = "end_time", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String endTime,
            @RequestParam(value = "approval_status", required = false) Integer status,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        return rsmTaskService.getTaskList(pageNumber, pageSize, startTime, endTime, status, keyword);
    }

    @ApiOperation("更新作业")
    @PutMapping("/tasks/{id}")
    public Result updateTask(
            @PathVariable("id") Integer id,
            @RequestBody RsmTask rsmTask
    ) {
        return rsmTaskService.updateTask(rsmTask, id);
    }


    @ApiOperation("审核作业")
    @PutMapping("/tasks/{id}/review")
    @PreAuthorize("hasAuthority('work:audit:list')")
    public Result approvalTask(
            @PathVariable("id") Integer id,
            @RequestBody RsmTask rsmTask
    ) {
        return rsmTaskService.approvalTask(id, rsmTask);
    }

    @ApiOperation("删除作业")
    @DeleteMapping("/tasks/{id}")
    public Result deleteTask(@PathVariable("id") Integer id) {
        return rsmTaskService.deleteTask(id);
    }

    @ApiOperation("查询单个作业")
    @GetMapping("/tasks/{id}")
    @PreAuthorize("hasAuthority('work:audit:')")
    public Result getTaskById(@PathVariable("id") Integer id) {
        return rsmTaskService.getTaskById(id);
    }

    @ApiOperation("Excel批量导入作业")
    @PostMapping("/task/import")
    public Result importTask(@RequestParam("file") MultipartFile file) {
        return rsmTaskService.importTask(file);
    }

}
