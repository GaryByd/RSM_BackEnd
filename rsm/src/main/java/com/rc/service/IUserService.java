package com.rc.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.rc.domain.dto.LoginFormDTO;
import com.rc.domain.dto.Result;
import com.rc.domain.dto.UserDTO;
import com.rc.domain.entity.User;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
public interface IUserService extends IService<User> {

    Result sendCode(String phone, HttpSession session);



    Result logout(String token);

    Result login(String code, HttpSession session);

    Result bandWithPasswd(LoginFormDTO loginFormDTO, HttpSession session);


//    Result bandWithPhone(String code, HttpSession session);
//    Result sign();
    
//    Result signCount();

//    Result updatePassword(LoginFormDTO loginForm);
//    Result loginWithPassword(LoginFormDTO loginForm, HttpSession session);
}
