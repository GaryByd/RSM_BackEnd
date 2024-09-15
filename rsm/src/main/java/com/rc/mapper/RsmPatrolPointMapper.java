package com.rc.mapper;

import com.rc.domain.entity.RsmPatrolPoint;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 巡查点表 Mapper 接口
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
public interface RsmPatrolPointMapper extends BaseMapper<RsmPatrolPoint> {

    RsmPatrolPoint getTaskPosition(Integer taskId, Integer positionId);
}
