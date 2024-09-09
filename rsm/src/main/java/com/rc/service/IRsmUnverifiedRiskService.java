package com.rc.service;

import com.rc.domain.dto.ItemsFormDTO;
import com.rc.domain.dto.Result;
import com.rc.domain.entity.RsmUnverifiedRisk;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 风险待查项表 服务类
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
public interface IRsmUnverifiedRiskService extends IService<RsmUnverifiedRisk> {

    Result getUnverifiedRiskByListId(Integer checklistId);

    Result unverifiedRiskDone(ItemsFormDTO itemsFormDTO, Integer checklistId, Integer itemId);
}
