package com.rc.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rc.domain.entity.User;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */

public interface UserMapper extends BaseMapper<User> {

    boolean updateUser(User user, long userId);
}
