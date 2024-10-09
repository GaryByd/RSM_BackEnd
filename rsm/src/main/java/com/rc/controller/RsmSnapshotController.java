package com.rc.controller;


import com.rc.domain.dto.Result;
import com.rc.domain.entity.RsmSnapshot;
import com.rc.service.IRsmSnapshotService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 随手拍问题表 前端控制器
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@RestController
@RequestMapping("/api/mp")
public class RsmSnapshotController {
    @Autowired
    private IRsmSnapshotService rsmSnapshotService;


    //分页查询
    @ApiOperation(value = "获取随手拍列表")
    @RequestMapping("/snapshots")
    public Object getSnapshotList(
            @RequestParam("page_number") Integer pageNumber,
            @RequestParam("page_size") Integer pageSize,
            @RequestParam(value = "property",required = false) Integer property


    ){
        return rsmSnapshotService.getSnapshotList(pageNumber, pageSize,property);
    }

    @ApiOperation(value = "获取单个随手拍")
    @RequestMapping("/snapshots/{id}")
    public Object getSnapshotById(@PathVariable("id") Long id){
        return rsmSnapshotService.getSnapshotById(id);
    }

    @ApiOperation(value = "添加随手拍")
    @PostMapping("/snapshots")
    public Result addSnapshot(
            @RequestBody RsmSnapshot rsmSnapshot
    ){
        return rsmSnapshotService.addSnapshot(rsmSnapshot);
    }

    @ApiOperation(value = "处理随手拍")
    @PutMapping("/snapshots/{id}/handle")
    @PreAuthorize("hasAuthority('snapshot:snapshotExamine:edit')")
    public Object doneSnapshot(
            @PathVariable("id") Long id,
            @RequestBody RsmSnapshot rsmSnapshot
    ){
        return rsmSnapshotService.doneSnapshot(id,rsmSnapshot);
    }

}
