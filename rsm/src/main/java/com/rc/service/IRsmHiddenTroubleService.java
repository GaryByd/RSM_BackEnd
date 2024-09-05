package com.rc.service;

import com.rc.domain.dto.Result;
import com.rc.domain.entity.RsmHiddenTrouble;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 隐患表 服务类
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
public interface IRsmHiddenTroubleService extends IService<RsmHiddenTrouble> {

    Result getHiddenTroubleList(Integer pageNumber, Integer pageSize, Integer status, String troubleClassify, String source);

    Result getHiddenTroubleById(Long id);

    Result addHiddenTrouble(RsmHiddenTrouble hiddenTrouble);

    Result handleHiddenTrouble(Long id, RsmHiddenTrouble hiddenTrouble);
}
