package com.rc.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.domain.entity.RsmSnapshot;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 随手拍问题表 Mapper 接口
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
public interface RsmSnapshotMapper extends BaseMapper<RsmSnapshot> {

    IPage<RsmSnapshot> getSnapShotList(Page<RsmSnapshot> page, String property, Integer status, Integer status1);

    int handelSnapshot(RsmSnapshot rsmSnapshot, Long id);
}
