package com.rc.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.domain.dto.LoginFormDTO;
import com.rc.domain.dto.Result;
import com.rc.domain.dto.UserDTO;
import com.rc.domain.entity.User;
import com.rc.mapper.UserMapper;
import com.rc.service.IUserService;
import com.rc.utils.Md5Util;
import com.rc.utils.RegexUtils;
import com.rc.utils.WeiChatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.rc.utils.RedisConstants.*;
import static com.rc.utils.SystemConstants.USER_NICK_NAME_PREFIX;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;



    @Override
    public Result sendCode(String phone, HttpSession session) {
        //校验手机号
       if(RegexUtils.isPhoneInvalid(phone)){
           //如果不符合返回
           return Result.fail("手机号格式错误");
       }
        //符合就生成验证码
        String code = RandomUtil.randomNumbers(6);
        //保存到redis
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY+phone,code,LOGIN_CODE_TTL, TimeUnit.MINUTES);
        System.out.println("OK");
        return Result.ok("success", (Object) code);

    }


//
    @Override
    public Result logout(String token) {
        //删除redis中的token
        stringRedisTemplate.delete(LOGIN_USER_KEY+token);
        //删除session中的token
        return Result.ok("再见我的宝贝", (Object) "成功退出");
    }

    @Override
    public Result login(String code, HttpSession session) {
        // 1. 获取 openId
        String openId = WeiChatUtil.getSessionId(code);
//        //假设
//        openId="owhoa7fITbB2gA1N4dxwWmjN8Xsw";

        // 2. 根据 openId 查询用户
        User user = query().eq("openid", openId).one();

        // 3. 如果用户不存在，返回错误信息
        if (user == null) {
            return Result.fail(402,"未绑定员工账号");
        }

        // 4. 生成 token
        String token = UUID.randomUUID().toString();

        // 5. 将 User 对象转换为 UserDTO
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        userDTO.setToken(token);
        // 6. 将 UserDTO 对象转换为 Map 并存储到 Redis 中
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                CopyOptions.create().setIgnoreNullValue(true)
                        .setFieldValueEditor((key, value) -> value != null ? value.toString() : ""));
        stringRedisTemplate.opsForHash().putAll(LOGIN_USER_KEY + token, userMap);

        // 7. 设置 Redis 过期时间
        stringRedisTemplate.expire(LOGIN_USER_KEY + token, LOGIN_USER_TTL, TimeUnit.MINUTES);

        // 8. 在 session 中保存用户信息
        session.setAttribute("user", userDTO);

        // 9. 返回 token 和用户信息
        return Result.ok("操作成功", userDTO);
    }

    @Override
    public Result bandWithPasswd(LoginFormDTO loginFormDTO, HttpSession session) {
        //获取密码
        String password = loginFormDTO.getPassWord();
        //获取WeiXincode
        String weiXinCode = loginFormDTO.getWeiXinCode();
        //获取username
        String userName = loginFormDTO.getUserName();
        //判断openId是否被绑定

        //判断是否为空
        if(weiXinCode==null) {
            return Result.fail("code_js为空");
        }
        //不为空
        //获取openid
        String openId = WeiChatUtil.getSessionId(weiXinCode);
//        //这里是假设
//        openId = "oK_8f5K8Zy2-YJ-JZ8Z2Z2Z2123Z2";
        //如果openid为空
        if(openId==null){
            return Result.fail("错误的code_js");
        }
        User user = query().eq("openid", openId).one();
        if(user!=null){
            return Result.fail("微信号已绑定");
        }
        //判断用户名是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        User user1 = query().eq("user_name", userName).one();
        if(user1!=null){
            return Result.fail("用户名已存在");
        }
        //用户名不存在
        //创建用户
        User newUser = new User();
        newUser.setOpenid(openId);
        newUser.setPassword(Md5Util.getMD5String(password));
        newUser.setUsername(loginFormDTO.getUserName());

        newUser.setPhone(loginFormDTO.getPhone() != null ?loginFormDTO.getPhone():"");
        newUser.setNickName(USER_NICK_NAME_PREFIX+RandomUtil.randomString(10));
        save(newUser);
        //user转为UserDTO
        UserDTO userDTO = new UserDTO();
        userDTO.setId(newUser.getId());
        userDTO.setNickname(newUser.getNickName());
        //头像
        userDTO.setAvatar(newUser.getIcon());
        //电话号码
        userDTO.setPhone_number(newUser.getPhone());
        return Result.ok("操作成功",userDTO);


    }

//    private User createUserWithPhone(String phone) {
//        //创建用户
//        User user = new User();
//        user.setPhone(phone);
//        user.setNickName(USER_NICK_NAME_PREFIX+RandomUtil.randomString(10));
//        save(user);
//        return user;
//    }



//    @Override
//    public Result bandWithPhone(String code, HttpSession session) {
//
//        //获取用户信息
//        UserDTO nowUser = UserHolder.getUser();
//        //从数据库中获取手机号
//        User user = query().eq("id",nowUser.getId()).one();
//        String phone = user.getPhone();
//        log.info("phone:", phone);
//
//        if(code==null){
//            return Result.fail("验证码为空");
//        }
//        //1.校验手机号
//        if(RegexUtils.isPhoneInvalid(phone)){
//            //2.校验验证码,返回错误信息
//            return Result.fail("手机号格式错误");
//        }
//        //不一致直接报错 redis
//        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY+phone);
//        if(cacheCode==null||!cacheCode.toString().equals(code)){
//            return Result.fail("验证码错误");
//        }
//        //4.一致，根据手机号查询用户
//        User newUser = query().eq("phone",phone).one();
//        //5.不存在就创建
//        if(newUser==null){
//            user = createUserWithPhone(phone);
//        }
//        return Result.ok("操作成功", (Object) "绑定成功");
//
////        //6.存在，保存用户信息到redis并返回
////        //生成token
////        String token = UUID.randomUUID().toString();
////        //将user对象转换成Hash存储
////        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
////        Map<String,Object> userMap = BeanUtil.beanToMap(userDTO,new HashMap<>(), CopyOptions.create().setIgnoreNullValue(true).setFieldValueEditor((key, value) -> value != null ? value.toString() : ""));
////        //存储
////        stringRedisTemplate.opsForHash().putAll(LOGIN_USER_KEY+token, userMap);
////        //7.返回token
////        session.setAttribute("user", BeanUtil.copyProperties(user, UserDTO.class));
////        stringRedisTemplate.expire(LOGIN_USER_KEY+token,LOGIN_USER_TTL,TimeUnit.MINUTES);
////        return Result.ok(token);
//    }


//    @Override
//    public Result signCount() {
//        Long userId = UserHolder.getUser().getId();
//        //获取日期
//        LocalDateTime now = LocalDateTime.now();
//        //拼接key
//        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
//        String key = USER_SIGN_KEY + userId + keySuffix;
//        //获取今天是本月的第几天
//        int dayOfMonth = now.getDayOfMonth();
//        //获取本月截止为止的连续签到
//        List<Long> result = stringRedisTemplate.opsForValue().bitField(
//                key, BitFieldSubCommands.create()
//                        .get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0)
//        );
//        if(result == null||result.isEmpty()){
//            return Result.ok();
//        }
//        Long num = result.get(0);
//        if(num==0||num==null){
//            return Result.ok();
//        }
//        //循环遍历
//        int count = 0;
//        while (true){
//            if((num & 1)==0){
//                break;
//            }else{
//                count++;
//            }
//            num>>>=1;
//        }
//        return Result.ok(count);
//    }
//
//
//    //更改密码
//    @Override
//    public Result updatePassword(LoginFormDTO loginForm) {
//        String phone = loginForm.getPhone();
//        if(RegexUtils.isPhoneInvalid(phone)){
//            return Result.fail("手机号格式错误");
//        }
//        if(loginForm.getPassword()==null){
//            return Result.fail("密码为空");
//        }
//        if(loginForm.getCode()==null){
//            return Result.fail("验证码为空");
//        }
//        //验证码错误
//        if(!loginForm.getCode().equals(stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY+phone))){
//            return Result.fail("验证码错误");
//        }
//        User user = query().eq("phone", phone).one();
//        if(user==null){
//            return Result.fail("出现未知错误请联系管理员");
//        }
//        String password = loginForm.getPassword();
//        String md5String = Md5Util.getMD5String(password);
//        user.setPassword(md5String);
//        updateById(user);
//        return Result.ok("修改成功!");
//    }
//
//
//    //查询用户模糊查询
//
//
//
//
//    @Override
//    public Result sign() {
//        //获取当前登入用户
//        Long userId = UserHolder.getUser().getId();
//        //获取日期
//        LocalDateTime now = LocalDateTime.now();
//        //拼接key
//        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
//        String key = USER_SIGN_KEY + userId + keySuffix;
//        //获取今天是本月的第几天
//        int dayOfMonth = now.getDayOfMonth();
//        //写入redis
//        stringRedisTemplate.opsForValue().setBit(key,dayOfMonth-1,true);
//        return Result.ok();
//    }

//    @Override
//    public Result loginWithPassword(LoginFormDTO loginForm, HttpSession session) {
//        //1.校验手机号和验证码
//        String pwd = loginForm.getPassword();
//        if(pwd==null){
//            return Result.fail("密码为空");
//        }
//        pwd = Md5Util.getMD5String(pwd);
//        //2.校验密码
//        User user = query().eq("password",pwd).one();
//        if(user==null){
//            return Result.fail("密码错误");
//        }
//        //生成token
//        String token = UUID.randomUUID().toString();
//        //将user对象转换成Hash存储
//        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
//        Map<String,Object> userMap = BeanUtil.beanToMap(userDTO,new HashMap<>(), CopyOptions.create().setIgnoreNullValue(true).setFieldValueEditor((key, value) -> value != null ? value.toString() : ""));
//        //存储
//        stringRedisTemplate.opsForHash().putAll(LOGIN_USER_KEY+token, userMap);
//        //7.返回token
//        session.setAttribute("user", BeanUtil.copyProperties(user, UserDTO.class));
//        stringRedisTemplate.expire(LOGIN_USER_KEY+token,LOGIN_USER_TTL,TimeUnit.MINUTES);
//        return Result.ok(token);
//    }

}
