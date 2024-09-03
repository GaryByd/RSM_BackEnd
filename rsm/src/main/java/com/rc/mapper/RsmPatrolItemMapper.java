package com.rc.mapper;

import com.rc.domain.dto.ItemsFormDTO;
import com.rc.domain.entity.RsmPatrolItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 巡检项表 Mapper 接口
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
public interface RsmPatrolItemMapper extends BaseMapper<RsmPatrolItem> {

    //根据巡查清单ID获取巡查列表
    public List<RsmPatrolItem> getPatrolItemByListId(Integer checklistId);

    public int patrolItemDone(ItemsFormDTO itemsFormDTO, Integer checklistId, Integer itemId);
}
