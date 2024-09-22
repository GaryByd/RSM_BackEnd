package com.rc.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.domain.dto.Result;
import com.rc.domain.dto.RsmTaskDTO;
import com.rc.domain.dto.TaskList;
import com.rc.domain.dto.UserDTO;
import com.rc.domain.entity.RsmRisk;
import com.rc.domain.entity.RsmTask;
import com.rc.mapper.RsmTaskMapper;
import com.rc.service.IRsmTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.utils.CacheClient;
import com.rc.utils.UserHolder;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cn.hutool.poi.excel.cell.CellUtil.getCellValue;
import static com.rc.utils.RedisConstants.CACHE_TTL;
import static com.rc.utils.RedisConstants.TASK_KEY;


/**
 * <p>
 * 作业表 服务实现类
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@Service
public class RsmTaskServiceImpl extends ServiceImpl<RsmTaskMapper, RsmTask> implements IRsmTaskService {

    @Autowired
    private RsmTaskMapper rsmTaskMapper;

    @Autowired
    private CacheClient cacheClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result addTask(RsmTask rsmTask) {
        UserDTO user = UserHolder.getUser();
        //创建时间
        rsmTask.setCreateTime(LocalDateTime.now());
        rsmTask.setUpdateTime(LocalDateTime.now());
        rsmTask.setCreateBy(user.getNickName());
        rsmTask.setUpdateBy(user.getNickName());


        boolean save = this.save(rsmTask);
        if (!save) {
            return Result.fail("添加失败");
        }
        rsmTask = this.getByIdWithCache(rsmTask.getId());
        return Result.ok("添加成功", rsmTask);
    }

    @Override
    public Result getTaskList(Integer pageNumber, Integer pageSize, String startTime, String endTime, Integer status, String keyword) {
        Page<RsmTask> page = new Page<>(pageNumber, pageSize);

        // 调用 Mapper 方法，执行分页查询
        IPage<RsmTask> taskListPage = rsmTaskMapper.getTaskList(page, startTime, endTime, status,keyword);
        TaskList taskList = new TaskList(taskListPage.getRecords(), taskListPage.getTotal());
        taskList.setTotal((long) taskList.getTaskListData().size());
        // 将结果封装到 Result 对象中返回
        return Result.ok("获取成功", taskList);

    }

    @Override
    public Result updateTask(RsmTask rsmTask, Integer id) {
        UserDTO user = UserHolder.getUser();
        rsmTask.setUpdateBy(user.getNickName());
        rsmTask.setUpdateTime(LocalDateTime.now());
        int updated = rsmTaskMapper.updateTask(rsmTask, id);
        if (updated == 0) {
            return Result.fail("修改失败");
        }
        //修改成功
        //删除缓存
        stringRedisTemplate.delete(TASK_KEY + id);
        rsmTask = this.getByIdWithCache(Long.valueOf(id));
        RsmTaskDTO rsmTaskDTO = new RsmTaskDTO();
        BeanUtils.copyProperties(rsmTask, rsmTaskDTO);
        return Result.ok("修改成功", rsmTaskDTO);
    }


    @Override
    public Result approvalTask(Integer id, RsmTask rsmTaskForm) {
        RsmTask rsmTask = this.getByIdWithCache(Long.valueOf(id));
        if (rsmTask == null) {
            return Result.fail("未找到该作业");
        }
        //修改这个作业的remark 和 status
        rsmTask.setApprovalStatus(rsmTaskForm.getApprovalStatus());
        rsmTask.setRemark(rsmTaskForm.getRemark());
        //设置修改时间
        rsmTask.setUpdateTime(LocalDateTime.now());
        //保存
        boolean success = this.updateById(rsmTask);
        if (!success) {
            return Result.fail("操作失败");
        }
        //删除redis
        stringRedisTemplate.delete(TASK_KEY + id);
        //操作成功
        return Result.ok("操作成功", (Object) "审核成功");
    }

    @Override
    public Result deleteTask(Integer id) {
        int deleted = rsmTaskMapper.deleteById(id);
        //判断是否删除成功
        if (deleted < 1) {
            return Result.fail("删除失败或没有该条数据");
        }
        //删除成功
        //删除缓存
        stringRedisTemplate.delete(TASK_KEY + id);
        return Result.ok("操作成功", (Object) "删除成功");
    }


    private RsmTask getByIdWithCache(Long id) {
        //缓存优化
        return cacheClient.queryWithPassThrough(
                TASK_KEY,
                id,
                RsmTask.class,
                this::getById,
                CACHE_TTL,
                TimeUnit.MINUTES
        );
    }
    @Override
    public Result getTaskById(Integer id) {
        //缓存优化
        RsmTask rsmTask = this.getByIdWithCache(Long.valueOf(id));
        if (rsmTask == null) {
            return Result.fail("未找到该作业");
        }
        return Result.ok("获取成功", rsmTask);
    }

    //缓存优化

    @Override
    public Result importTask(MultipartFile file) {
        List<RsmTask> taskList = new ArrayList<>();
        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (int i = 1; i < rows; i++) {  // 从第2行开始，跳过标题行
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                RsmTask task = new RsmTask();
                task.setId((long) row.getCell(0).getNumericCellValue());  // id
                task.setTaskName((String) getCellValue(row.getCell(1)));   // task_name
                task.setTypeName((String) getCellValue(row.getCell(2)));   // type_name
                task.setDeptName((String) getCellValue(row.getCell(3)));   // dept_name
                task.setStartTime(LocalDateTime.parse((CharSequence) getCellValue(row.getCell(4)), formatter)); // start_time
                task.setEndTime(LocalDateTime.parse((CharSequence) getCellValue(row.getCell(5)), formatter));   // end_time
                task.setRiskId((long) row.getCell(6).getNumericCellValue()); // risk_id
                task.setMandateHolder((String) getCellValue(row.getCell(7))); // mandate_holder
                task.setApprovalStatus((int) row.getCell(8).getNumericCellValue()); // approval_status
                task.setReviewer((String) getCellValue(row.getCell(9)));  // reviewer
                task.setTaskDesc((String) getCellValue(row.getCell(10)));  // task_desc
                task.setPositionId((Long) getCellValue(row.getCell(11))); // position_id
                task.setCreateBy((String) getCellValue(row.getCell(12))); // create_by
                task.setRemark((String) getCellValue(row.getCell(13)));    // remark

                // 设置创建时间和修改时间为当前时间(如果表里面没有时间)
                if (row.getCell(14) == null || StringUtils.isBlank((String) getCellValue(row.getCell(14)))) {
                    task.setCreateTime(LocalDateTime.now());
                } else {
                    String createTimeStr = (String) getCellValue(row.getCell(14));
                    task.setCreateTime(LocalDateTime.parse(createTimeStr, formatter));
                }

                task.setUpdateBy((String) getCellValue(row.getCell(15)));

                if (row.getCell(15) == null || StringUtils.isBlank((String) getCellValue(row.getCell(15)))) {
                    task.setUpdateTime(LocalDateTime.now());
                } else {
                    String updateTimeStr = (String) getCellValue(row.getCell(16));
                    task.setUpdateTime(LocalDateTime.parse(updateTimeStr, formatter));
                }



                taskList.add(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Result.ok("文件读取失败");
        }

        if (!taskList.isEmpty()) {
            // 使用批量插入
            int batchSize = 500;
            for (int i = 0; i < taskList.size(); i += batchSize) {
                int end = Math.min(i + batchSize, taskList.size());
                List<RsmTask> batchList = taskList.subList(i, end);
                rsmTaskMapper.insertBatch(batchList);
            }
            return Result.ok("操作成功", (Object) ("导入成功，共导入 " + taskList.size() + " 条数据"));
        } else {
            return Result.fail("没有有效数据");
        }
    }

}
