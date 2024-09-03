package com.rc.service.impl;

import com.rc.domain.dto.ItemsFormDTO;
import com.rc.domain.dto.PatrolList;
import com.rc.domain.dto.Result;
import com.rc.domain.entity.RsmPatrolItem;
import com.rc.mapper.RsmPatrolItemMapper;
import com.rc.service.IRsmPatrolItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 巡检项表 服务实现类
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@Service
public class RsmPatrolItemServiceImpl extends ServiceImpl<RsmPatrolItemMapper, RsmPatrolItem> implements IRsmPatrolItemService {

    @Autowired
    private RsmPatrolItemMapper patrolItemMapper;

    @Override
    public Result getPatrolItemByListId(Integer checklistId) {
        //使用rsm_patrol_list的checklistId查询rsm_patrol_item表
        List<RsmPatrolItem> patrolItemByListId = patrolItemMapper.getPatrolItemByListId(checklistId);
        int total = patrolItemByListId.size();
        PatrolList patrolList = new PatrolList(patrolItemByListId, (long) total);
        return Result.ok("查询成功",patrolList);
    }



    @Override
    public Result patrolItemDone(ItemsFormDTO itemsFormDTO, Integer checklistId, Integer itemId) {
        int updatedRows  = patrolItemMapper.patrolItemDone(itemsFormDTO, checklistId, itemId);
        if(updatedRows == 0){
            return Result.fail("修改失败");
        }
        //操作成功
        //查询一下子
        RsmPatrolItem rsmPatrolItem = query().eq("id", itemId).eq("patrol_list_id", checklistId).one();
        return Result.ok("修改成功",rsmPatrolItem);
    }
}
