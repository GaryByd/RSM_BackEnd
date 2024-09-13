package com.rc.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rc.domain.dto.HiddenList;
import com.rc.domain.dto.Result;
import com.rc.domain.dto.UserDTO;
import com.rc.domain.entity.RsmHiddenTrouble;
import com.rc.mapper.RsmHiddenTroubleMapper;
import com.rc.service.IRsmHiddenTroubleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 隐患表 服务实现类
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@Service
public class RsmHiddenTroubleServiceImpl extends ServiceImpl<RsmHiddenTroubleMapper, RsmHiddenTrouble> implements IRsmHiddenTroubleService {

    @Autowired
    private RsmHiddenTroubleMapper rsmHiddenTroubleMapper;

    @Override
    public Result getHiddenTroubleList(Integer pageNumber, Integer pageSize, Integer status, String troubleClassify, String source, String keyword) {
        // 创建分页对象
        Page<RsmHiddenTrouble> page = new Page<>(pageNumber, pageSize);

        // 调用 Mapper 方法，执行分页查询
        IPage<RsmHiddenTrouble> HiddenTroublePage = rsmHiddenTroubleMapper.getHiddenTroubleList(page, status, troubleClassify, source,keyword);
        HiddenList hidden_list = new HiddenList(HiddenTroublePage.getRecords(), HiddenTroublePage.getTotal());
        hidden_list.setTotal((long) hidden_list.getHiddenListData().size());
        // 将结果封装到 Result 对象中返回
        return Result.ok("获取成功",hidden_list);
    }

    @Override
    public Result getHiddenTroubleById(Long id) {
        RsmHiddenTrouble hiddenTrouble = rsmHiddenTroubleMapper.selectById(id);
        if(hiddenTrouble==null){
            return Result.fail("未查询到该隐患信息");
        }
        return Result.ok("查询成功",hiddenTrouble);
    }

    @Override
    public Result addHiddenTrouble(RsmHiddenTrouble hiddenTrouble) {
//        UserDTO user = UserHolder.getUser();
//        Long userId = user.getId();
        Long userId = 2L;
        hiddenTrouble.setCreatorId(userId);
        //设置时间
        hiddenTrouble.setCreateTime(java.time.LocalDateTime.now());
        hiddenTrouble.setUpdateTime(java.time.LocalDateTime.now());
        boolean saved = this.save(hiddenTrouble);
        if(!saved){
            return Result.fail("添加失败");
        }
        return Result.ok("操作成功", (Object) "添加成功");
    }

    @Override
    public Result handleHiddenTrouble(Long id, RsmHiddenTrouble hiddenTrouble) {
        UserDTO user = UserHolder.getUser();
        Long userId = user.getUserId();

        hiddenTrouble.setHandlerId(userId);
        hiddenTrouble.setUpdateTime(java.time.LocalDateTime.now());
        //上传处理时间
        hiddenTrouble.setRectifyTime(java.time.LocalDateTime.now());
        boolean updated = rsmHiddenTroubleMapper.updateHiddenById(hiddenTrouble,id);
        if(!updated){
            return Result.fail("修改失败");
        }
        return Result.ok("操作成功", rsmHiddenTroubleMapper.selectById(id));
    }

}
