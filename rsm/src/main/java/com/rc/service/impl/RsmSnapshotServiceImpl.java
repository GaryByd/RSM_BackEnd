package com.rc.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.domain.dto.Result;
import com.rc.domain.dto.SnapShotList;
import com.rc.domain.dto.UserDTO;
import com.rc.domain.entity.RsmSnapshot;
import com.rc.domain.entity.User;
import com.rc.mapper.RsmSnapshotMapper;
import com.rc.service.IRsmSnapshotService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * <p>
 * 随手拍问题表 服务实现类
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@Service
public class RsmSnapshotServiceImpl extends ServiceImpl<RsmSnapshotMapper, RsmSnapshot> implements IRsmSnapshotService {


    @Autowired
    private  RsmSnapshotMapper rsmSnapshotMapper;
    @Override
    public Result getSnapshotList(Integer pageNumber, Integer pageSize, String property, Integer status) {
        // 创建分页对象
        Page<RsmSnapshot> page = new Page<>(pageNumber, pageSize);

        // 调用 Mapper 方法，执行分页查询
        IPage<RsmSnapshot> snapShotListPage = rsmSnapshotMapper.getSnapShotList(page, property, status, status);
        SnapShotList snapShotList = new SnapShotList (snapShotListPage.getRecords(), snapShotListPage.getTotal());
        // 将结果封装到 Result 对象中返回
        return Result.ok("获取成功",snapShotList);
    }

    @Override
    public Result getSnapshotById(Long id) {
        RsmSnapshot rsmSnapshot = rsmSnapshotMapper.selectById(id);
        return Result.ok("获取成功",rsmSnapshot);
    }

    @Override
    public Result addSnapshot(RsmSnapshot rsmSnapshot) {
        UserDTO user = UserHolder.getUser();
        Long userId = user.getId();
//        Long userId=2L;
        rsmSnapshot.setCreatorId(userId);

        //更新时间
        rsmSnapshot.setCreateTime(LocalDateTime.now());
        rsmSnapshot.setUpdateTime(LocalDateTime.now());
        //名字
//        rsmSnapshot.setCreatorName("小罗");
        rsmSnapshot.setCreatorName(user.getNickName());


        boolean saved = this.save(rsmSnapshot);
        if (!saved) {
            return Result.fail("添加失败");
        }
        return Result.ok("添加成功",rsmSnapshotMapper.selectById(userId));
    }

    @Override
    public Result doneSnapshot(Long id, RsmSnapshot rsmSnapshot) {
        UserDTO user = UserHolder.getUser();
        Long userId = user.getId();
//        Long userId = 2L;
        //更新时间
        rsmSnapshot.setHandlerTime(LocalDateTime.now());
        rsmSnapshot.setHandlerId(userId);
        int updated = rsmSnapshotMapper.handelSnapshot(rsmSnapshot);
        if (updated<=0) {
            return Result.fail("修改失败");
        }
        return Result.ok("修改成功",rsmSnapshotMapper.selectById(id));
    }

}
