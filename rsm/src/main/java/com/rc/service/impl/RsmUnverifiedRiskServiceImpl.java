package com.rc.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rc.domain.dto.ItemsFormDTO;
import com.rc.domain.dto.PatrolList;
import com.rc.domain.dto.Result;
import com.rc.domain.entity.RsmPatrolList;
import com.rc.domain.entity.RsmUnverifiedRisk;
import com.rc.mapper.RsmPatrolListMapper;
import com.rc.mapper.RsmUnverifiedRiskMapper;
import com.rc.service.IRsmUnverifiedRiskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.rc.utils.RedisConstants.HIDDEN_CHECKED_KEY;
import static com.rc.utils.RedisConstants.LIST_CHECKED_KEY;

/**
 * <p>
 * 风险待查项表 服务实现类
 * </p>
 *
 * @author 罗佳炜
 * @since 2024-09-03
 */
@Service
public class RsmUnverifiedRiskServiceImpl extends ServiceImpl<RsmUnverifiedRiskMapper, RsmUnverifiedRisk> implements IRsmUnverifiedRiskService {
    @Autowired
    private RsmUnverifiedRiskMapper rsmUnverifiedRiskMapper;

    @Autowired
    private RsmPatrolListMapper rsmPatrolListMapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Override
    public Result getUnverifiedRiskByListId(Integer checklistId) {
        //使用rsm_patrol_list的checklistId查询rsm_patrol_item表
        List<RsmUnverifiedRisk> rsmUverifiedRiskList = rsmUnverifiedRiskMapper.getUnverifiedRiskByListId(checklistId);
        int total = rsmUverifiedRiskList.size();
        PatrolList patrolList = new PatrolList(rsmUverifiedRiskList, (long) total);
        return Result.ok("查询成功",patrolList);
    }



    @Override
    public Result unverifiedRiskDone(ItemsFormDTO itemsFormDTO, Integer checklistId, Integer itemId) {

        int status = itemsFormDTO.getStatus();
        //发送过来的状态为2 则为已发现隐患
        String key = LIST_CHECKED_KEY + itemId;
        Double score = stringRedisTemplate.opsForZSet().score( key, checklistId.toString());
        if(score!=null){
            return Result.fail("已经完成巡查，不能重复完成巡查");
        }
        //修改PatrolList表的checked_count+1
        int updated = rsmPatrolListMapper.update(
                new RsmPatrolList(), // 实体对象，可为空或者是新实例
                new UpdateWrapper<RsmPatrolList>()
                        .setSql("checked_count=checked_count+1")
                        .eq("id", checklistId)
        );
        stringRedisTemplate.opsForZSet().add(key, checklistId.toString(),System.currentTimeMillis());
        if(updated==0){
            return Result.fail("checked_count修改失败");
        }

        if(status==2){
            //PatrolList表的hidden_trouble_count+1
            int hiddenUpdated = rsmPatrolListMapper.update(
                    new RsmPatrolList(), // 实体对象，可为空或者是新实例
                    new UpdateWrapper<RsmPatrolList>()
                            .setSql("hidden_trouble_count=hidden_trouble_count+1")
                            .eq("id", checklistId)
            );
            if(hiddenUpdated==0){
                return Result.fail("hidden_trouble_count修改失败");
            }
        }
        //日常更新status 状态使用ItemId
        int updatedRows  = rsmUnverifiedRiskMapper.unverifiedRiskDone(itemsFormDTO, itemId);
        if(updatedRows == 0){
            return Result.fail("status修改失败,没有这个ItemId");
        }

        //查询状态
        RsmUnverifiedRisk rsmUnverifiedRisk = rsmUnverifiedRiskMapper.selectById(itemId);
        return Result.ok("修改成功",rsmUnverifiedRisk);
    }
}
