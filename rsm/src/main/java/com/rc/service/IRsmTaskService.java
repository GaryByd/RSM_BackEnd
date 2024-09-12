package com.rc.service;

import com.rc.domain.dto.Result;
import com.rc.domain.entity.RsmTask;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 作业表 服务类
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
public interface IRsmTaskService extends IService<RsmTask> {

    Result addTask(RsmTask rsmTask);

    Result getTaskList(Integer pageNumber, Integer pageSize, String startTime, String endTime, Integer status, String keyword);

    Result updateTask(RsmTask rsmTask, Integer id);

    Result approvalTask(Integer id, RsmTask rsmTask);

    Result deleteTask(Integer id);

    Result getTaskById(Integer id);

    Result importTask(MultipartFile filePath);
}
