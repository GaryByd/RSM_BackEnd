package com.rc.service.impl;

import com.rc.domain.dto.Result;
import com.rc.domain.entity.RsmPatrolPoint;
import com.rc.mapper.RsmPatrolPointMapper;
import com.rc.service.IRsmPatrolPointService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.utils.CacheClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.rc.utils.RedisConstants.CACHE_TTL;
import static com.rc.utils.RedisConstants.PATROL_POINT_KEY;

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

    @Autowired
    private CacheClient cacheClient;



    private RsmPatrolPoint getByIdWithCache(Long id) {
        //缓存优化
        return cacheClient.queryWithPassThrough(
                PATROL_POINT_KEY,
                id,
                RsmPatrolPoint.class,
                this::getById,
                CACHE_TTL,
                TimeUnit.MINUTES
        );
    }
    @Override
    public Result getPatrolPointById(Integer pointId) {
        //缓存优化
        RsmPatrolPoint rsmPatrolPoint = this.getByIdWithCache(Long.valueOf(pointId));
        //没有找到
        if(rsmPatrolPoint==null){
            return Result.fail("未查询到该巡查点信息");
        }
        return Result.ok("操作成功",rsmPatrolPoint);
    }


    @Override
    public Result getTaskPosition(Integer taskId, Integer positionId) {
        RsmPatrolPoint rsmPatrolPoint = this.getByIdWithCache(Long.valueOf(positionId));
        //没有找到
        if(rsmPatrolPoint==null){
            return Result.fail("未查询到该巡查点信息");
        }
        return Result.ok("操作成功",rsmPatrolPoint);

    }

    @Override
    public Result getPatrolPointList() {
        return Result.ok("操作成功", patrolPointMapper.selectList(null));
    }
}
