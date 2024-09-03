package com.rc.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.domain.entity.RsmPatrolList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 巡查清单表 Mapper 接口
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
public interface RsmPatrolListMapper extends BaseMapper<RsmPatrolList> {

    IPage<RsmPatrolList> getPatrolList(Page<RsmPatrolList> page, String startTime, String endTime, Integer status);
}
