package com.rc.service;

import com.rc.domain.dto.Result;
import com.rc.domain.entity.RsmRisk;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 风险库表 服务类
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
public interface IRsmRiskService extends IService<RsmRisk> {

    Result getRiskById(Integer riskId);

    Result getRiskList();

}
