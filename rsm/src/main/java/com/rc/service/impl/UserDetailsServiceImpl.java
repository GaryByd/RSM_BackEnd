package com.rc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.rc.domain.dto.LoginUser;
import com.rc.domain.dto.UserDTO;
import com.rc.domain.entity.User;
import com.rc.exception.EmptyUserStatusException;
import com.rc.mapper.SysMenuMapper;
import com.rc.mapper.UserMapper;
import com.rc.utils.WeiChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    SysMenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String openId) throws UsernameNotFoundException {

        // 2. 根据 openId 查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getOpenid, openId);
        User user = userMapper.selectOne(queryWrapper);
        // 3. 如果用户不存在，返回错误信息
        if (user == null) {
            throw new EmptyUserStatusException("未绑定员工账号");
        }
//        ArrayList<String> list = new ArrayList<>();
//        list.add("user");
        List<String> list = menuMapper.selectPermsByUserId(user.getId());
        // 13. 更新用户登录时间
        user.setLoginDate(LocalDateTime.now());
        // 14. 更新用户登录 IP（假设你有获取 IP 的逻辑）
        userMapper.updateById(user);
        UserDTO userDTO = new UserDTO(user.getId(),user.getNickName(),user.getIcon(),user.getPhone(),user.getRemark());
        // 将用户信息封装到UserDetails实现类中
        return new LoginUser(userDTO,list);
    }
}
