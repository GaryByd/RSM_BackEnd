package com.rc.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.domain.dto.HiddenList;
import com.rc.domain.dto.UserList;
import com.rc.domain.entity.RsmHiddenTrouble;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 隐患表 Mapper 接口
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
public interface RsmHiddenTroubleMapper extends BaseMapper<RsmHiddenTrouble> {

    IPage<RsmHiddenTrouble> getHiddenTroubleList(Page<RsmHiddenTrouble> page, Integer status, String troubleClassify, String source, String keyword);

    boolean updateHiddenById(RsmHiddenTrouble hiddenTrouble, Long id);


    List<RsmHiddenTrouble> getMyHiddenTrouble(Long userId);
}
