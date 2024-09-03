package com.rc.controller;


import cn.hutool.core.bean.BeanUtil;
import com.rc.domain.dto.LoginFormDTO;
import com.rc.domain.dto.Result;
import com.rc.domain.dto.UserDTO;
import com.rc.domain.entity.User;
import com.rc.service.IUserService;
import com.rc.utils.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Slf4j
@Api(tags = "用户登入系统")
@RestController
@RequestMapping("/api/mp")
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 登出功能
     * @return 无
     */
    @ApiOperation(value = "退出登入")
    @PostMapping("/login/exit")
    public Result logout(@RequestHeader("Authorization") String authorization) {
        return userService.logout(authorization);
    }


    @ApiOperation(value = "微信一键登入")
    @PostMapping("/login/weixin")
    public Result login(@RequestBody String code,HttpSession session){
        return userService.login(code,session);
    }


    /**
      * 登录功能
      * @param  ，包含手机号、验证码；或者手机号、密码
     *
     */
    @ApiOperation(value = "验证验证码")
    @PostMapping("/bindEmloyeeAccount")
    public Result bandWithPasswd(@RequestBody LoginFormDTO loginForm, HttpSession session){
        //实现登入功能
        return userService.bandWithPasswd(loginForm,session);
    }





//
//    /**
//     //     * 登录功能
//     //     * @param  ，包含手机号、验证码；或者手机号、密码
//     //     */
//    @ApiOperation(value = "验证验证码")
//    @PostMapping("/login/phone")
//    public Result bandWithPasswd(@RequestBody String code, HttpSession session){
//        //实现登入功能
//        return userService.bandWithPasswd(code,session);
//    }
//    /**
//     *
//     * @param id
//     * @return ok
//     */
//    //根据id查用户
//    @ApiOperation(value = "根据id查询用户")
//    @GetMapping("/users/{id}")
//    public Result queryUserById(@PathVariable("id") Long id) {
//        // 查询详情
//        User user = userService.getById(id);
//        if (user == null) {
//            return Result.ok();
//        }
//        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
//        // 返回
//        return Result.ok(userDTO);
//    }
//    /**
//     * 发送手机验证码
//     */
//    @ApiOperation(value = "发送验证码")
//    @PostMapping("/smscode/send")
//    public Result sendCode(@RequestBody LoginFormDTO loginFormDTO, HttpSession session) {
//        log.info("发送验证码短信验证码，手机号：{}", loginFormDTO.getPhone());
//        return userService.sendCode(loginFormDTO.getPhone(), session);
//    }
//
//    /**
//     * 登录功能
//     * @param  ，包含手机号、验证码；或者手机号、密码
//     */
//    @ApiOperation(value = "验证验证码")
//    @PostMapping("/login/phone")
//    public Result bandWithPasswd(@RequestBody String code, HttpSession session){
//        //实现登入功能
//        return userService.bandWithPasswd(code,session);
//    }
//    @ApiOperation(value = "签到")
//    @PostMapping("/sign")
//    public Result sign(){
//        return userService.sign();
//    }

//    @ApiOperation(value = "查询签到次数")
//    @GetMapping("/sign/count")
//    public Result signCount(){
//        return userService.signCount();
//    }


//    @ApiOperation(value = "使用密码登入")
//    @PostMapping("/login/pwd")
//    public Result login2(@RequestBody LoginFormDTO loginForm, HttpSession session){
//        //实现登入功能
//        return userService.loginWithPassword(loginForm,session);
//    }
//
//    @ApiOperation(value = "更改密码")
//    @PostMapping("/users/password")
//    public Result updatePassword(@RequestBody LoginFormDTO loginForm){
//        return userService.updatePassword(loginForm);
//    }

}
