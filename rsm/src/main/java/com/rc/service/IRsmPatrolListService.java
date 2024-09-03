package com.rc.service;

import com.rc.domain.dto.Result;
import com.rc.domain.entity.RsmPatrolList;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 巡查清单表 服务类
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
public interface IRsmPatrolListService extends IService<RsmPatrolList> {


    Result getPatrolList(Integer pageNumber, Integer pageSize, String startTime, String endTime, Integer status);
}
