package com.rc.controller;


import com.rc.domain.dto.Result;
import com.rc.domain.entity.RsmHiddenTrouble;
import com.rc.service.IRsmHiddenTroubleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 隐患表 前端控制器
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */

@Api(tags = "隐患")
@RestController
@RequestMapping("/api/mp")
public class RsmHiddenTroubleController {

    @Autowired private IRsmHiddenTroubleService rsmHiddenTroubleService;
    @ApiOperation(value = "获取隐患列表(业务入口)")
    @RequestMapping("/hiddens")
    public Result getHiddenTroubleList(
            @RequestParam("page_number") Integer pageNumber,
            @RequestParam("page_size") Integer pageSize,
            @RequestParam(value = "status",required = false) Integer status,
            @RequestParam(value = "trouble_classify",required = false) String troubleClassify,
            @RequestParam(value = "source",required = false) String source,
            @RequestParam(value = "keyword",required = false) String keyword
    ) {
        return rsmHiddenTroubleService.getHiddenTroubleList(pageNumber, pageSize, status, troubleClassify, source,keyword);
    }

    @ApiOperation("获取单个隐患")
    @RequestMapping("/hiddens/{id}")
    public Result getHiddenTroubleById(@PathVariable("id") Long id) {
        return rsmHiddenTroubleService.getHiddenTroubleById(id);
    }

    @ApiOperation("上传隐患")
    @PostMapping("/hiddens")
    public Result addHiddenTrouble(@RequestBody RsmHiddenTrouble hiddenTrouble) {
        return rsmHiddenTroubleService.addHiddenTrouble(hiddenTrouble);
    }

    @ApiOperation("处理隐患")
    @PutMapping("/hiddens/{id}/handle")
    public Result handleHiddenTrouble(@PathVariable("id") Long id, @RequestBody RsmHiddenTrouble hiddenTrouble) {
        return rsmHiddenTroubleService.handleHiddenTrouble(id, hiddenTrouble);
    }

}
