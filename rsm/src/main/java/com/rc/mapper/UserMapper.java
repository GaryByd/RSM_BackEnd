package com.rc.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rc.domain.dto.UserDTO;
import com.rc.domain.entity.RsmTask;
import com.rc.domain.entity.User;

import java.util.List;

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


    List<UserDTO> getUserList(String keyword);
}
