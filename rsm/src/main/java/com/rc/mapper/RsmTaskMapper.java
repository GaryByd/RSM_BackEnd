package com.rc.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.domain.entity.RsmTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 作业表 Mapper 接口
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
public interface RsmTaskMapper extends BaseMapper<RsmTask> {

    IPage<RsmTask> getTaskList(Page<RsmTask> page, String startTime, String endTime, Integer status, String keyword);

    int updateTask(RsmTask rsmTask, Integer id);

    void insertBatch(List<RsmTask> batchList);
}
