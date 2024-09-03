package com.rc.service.impl;

import com.rc.domain.dto.Result;
import com.rc.domain.entity.RsmPatrolPoint;
import com.rc.mapper.RsmPatrolPointMapper;
import com.rc.service.IRsmPatrolPointService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 巡查点表 服务实现类
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@Service
public class RsmPatrolPointServiceImpl extends ServiceImpl<RsmPatrolPointMapper, RsmPatrolPoint> implements IRsmPatrolPointService {
    @Autowired
    private RsmPatrolPointMapper patrolPointMapper;
    @Override
    public Result getPatrolPointByListId(Integer pointId) {
        RsmPatrolPoint rsmPatrolPoint = query()
                .eq("id", pointId)
                .one();
        return Result.ok("查询成功",rsmPatrolPoint);
    }
}
