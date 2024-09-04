package com.rc.service.impl;

import com.rc.domain.dto.Result;
import com.rc.domain.entity.RsmRisk;
import com.rc.mapper.RsmRiskMapper;
import com.rc.service.IRsmRiskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public Result getRiskById(Integer riskId) {
        return Result.ok("操作成功",this.getById(riskId));
    }
}
