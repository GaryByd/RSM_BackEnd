package com.rc.service.impl;

import com.rc.domain.dto.Result;
import com.rc.domain.entity.RsmRisk;
import com.rc.mapper.RsmRiskMapper;
import com.rc.service.IRsmRiskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.utils.CacheClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.rc.utils.RedisConstants.CACHE_TTL;
import static com.rc.utils.RedisConstants.RISK_KEY;

/**
 * <p>
 * 风险库表 服务实现类
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@Service
public class RsmRiskServiceImpl extends ServiceImpl<RsmRiskMapper, RsmRisk> implements IRsmRiskService {
    @Autowired
    private RsmRiskMapper rsmRiskMapper;

    @Autowired
    private CacheClient cacheClient;
    @Override
    public Result getRiskById(Integer riskId) {
        RsmRisk rsmRisk = cacheClient.queryWithPassThrough(
                RISK_KEY,
                riskId,
                RsmRisk.class,
                rsmRiskMapper::selectById,
                CACHE_TTL,
                TimeUnit.MINUTES
        );
        //没有找到
        if(rsmRisk==null){
            return Result.fail("未查询到该风险信息");
        }
        return Result.ok("操作成功",rsmRisk);
    }

    @Override
    public Result getRiskList() {
        return Result.ok("操作成功",this.list());
    }
}
