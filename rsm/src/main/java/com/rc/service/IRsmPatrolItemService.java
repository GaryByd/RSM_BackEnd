package com.rc.service;

import com.rc.domain.dto.ItemsDoneDTO;
import com.rc.domain.dto.Result;
import com.rc.domain.entity.RsmPatrolItem;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 巡检项表 服务类
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
public interface IRsmPatrolItemService extends IService<RsmPatrolItem> {

    Result getPatrolItemByListId(Integer checklistId);

    Result patrolItemDone(ItemsDoneDTO itemsDoneDTO, Integer checklistId, Integer itemId);
}
