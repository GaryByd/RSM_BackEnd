package com.rc.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.rc.domain.dto.CheckedList;
import com.rc.domain.dto.Result;

import com.rc.domain.entity.RsmPatrolList;
import com.rc.mapper.RsmPatrolListMapper;
import com.rc.service.IRsmPatrolListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 巡查清单表 服务实现类
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@Service
public class RsmPatrolListServiceImpl extends ServiceImpl<RsmPatrolListMapper, RsmPatrolList> implements IRsmPatrolListService {

    @Autowired
    private RsmPatrolListMapper patrolListMapper;

    public Result getPatrolList(Integer pageNumber, Integer pageSize, String startTime, String endTime, Integer status) {
        // 创建分页对象
        Page<RsmPatrolList> page = new Page<>(pageNumber, pageSize);

        // 调用 Mapper 方法，执行分页查询
        IPage<RsmPatrolList> patrolListPage = patrolListMapper.getPatrolList(page, startTime, endTime, status);
        CheckedList checklist_list = new CheckedList(patrolListPage.getRecords(), patrolListPage.getTotal());
        checklist_list.setTotal((long) checklist_list.getCheckedListData().size());
        // 将结果封装到 Result 对象中返回
        return Result.ok("获取成功",checklist_list);
    }
}
