package com.rc.mapper;

import com.rc.domain.dto.ItemsFormDTO;
import com.rc.domain.entity.RsmUnverifiedRisk;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 风险待查项表 Mapper 接口
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
public interface RsmUnverifiedRiskMapper extends BaseMapper<RsmUnverifiedRisk> {

    int unverifiedRiskDone(ItemsFormDTO itemsFormDTO, Integer itemId);

    List<RsmUnverifiedRisk> getUnverifiedRiskByListId(Integer itemId);
}
