package com.rc.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rc.domain.dto.*;
import com.rc.domain.entity.User;
import com.rc.mapper.UserMapper;
import com.rc.service.IUserService;
import com.rc.utils.*;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.rc.utils.RedisConstants.*;
import static com.rc.utils.KeyContents.*;

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
    private PasswordEncoder passwordEncoder;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;
    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private CacheClient cacheClient;

    @Override
    public Result verifyCode(PhoneDTO phoneDTO, HttpSession session) {
        String phone = phoneDTO.getPhoneNumber();
        String code = phoneDTO.getCode();

        if(code==null){
            return Result.fail("验证码为空");
        }
        //1.校验手机号
        if(RegexUtils.isPhoneInvalid(phone)){
            //2.校验验证码,返回错误信息
            return Result.fail("手机号格式错误");
        }
        //不一致直接报错 redis
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY+phone);
        if(cacheCode==null||!cacheCode.equals(code)){
            return Result.fail("验证码错误");
        }
        //验证成功
        return Result.ok("操作成功", (Object) "绑定成功");
    }

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
        //解析code里面的code数据 json
        String code_js = (String) JSONUtil.parse(code).getByPath("code");
        // 1. 使用 code 通过 WeiChatUtil 获取 openId
        String openId = WeiChatUtil.getSessionId(code_js);
        //假设openid
        openId = "owhoa7fITbB2gA1N4dxwWmjN8Xsw";
        // 2. 检查 openId 是否有效
        if (openId == null || openId.isEmpty()) {
            return Result.fail(401, "无效的微信code，无法获取openId");
        }

        // 5. 使用 openId 封装 Authentication 对象（不需要密码）
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(openId, null, null);

        // 6. 通过 AuthenticationManager 的 authenticate 方法进行用户认证
        Authentication authenticated = authenticationManager.authenticate(authenticationToken);

        // 7. 在 Authentication 中获取用户信息
        LoginUser loginUser = (LoginUser) authenticated.getPrincipal();


        Long userId = loginUser.getUser().getUserId();
        //UUID token
        String tokenId = JwtUtil.getUUID();
        //8.认证通过生成token
        String token = JwtUtil.createJWT(tokenId, String.valueOf(userId),2592000000L);

        // 10. 将 UserDTO 对象转换为 Map 并存储到 Redis 中
        Map<String, Object> userMap = BeanUtil.beanToMap(loginUser, new HashMap<>(),
                CopyOptions.create().setIgnoreNullValue(true)
                        .setFieldValueEditor((key, value) -> value != null ? value.toString() : ""));
        stringRedisTemplate.opsForHash().putAll(LOGIN_USER_KEY + token, userMap);
        // 11. 设置 Redis 过期时间
        stringRedisTemplate.expire(LOGIN_USER_KEY + token, LOGIN_USER_TTL, TimeUnit.MINUTES);
        // 12. 在 session 中保存用户信息
        session.setAttribute("user", loginUser.getUser());
        // 15. 返回 token 和用户信息
        return Result.ok("操作成功", (Object) token);
    }

    @Override
    public Result bandWithPasswd(LoginFormDTO loginFormDTO, HttpSession session) throws Exception {
        //获取密码
        //私钥解密
        String password = RsaUtil.decryptByPrivateKey(loginFormDTO.getPassWord(),PRIVATE_KEY);
//        System.out.println("解密后:"+password);
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
//        openId = "owhoa7fITbB2gA1N4dxwWmjN8Xsw";
        //如果openid为空
        if(openId==null){
            return Result.fail("错误的code_js");
        }
        User user = query().eq("open_id", openId).one();
        if(user!=null){
            return Result.fail("微信号已绑定");
        }

        //判断用户名是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        User user1 = query().eq("user_name", userName).one();
        //用户名不存在
        if(user1==null){
            return Result.fail("用户名不存在");
        }
        //open_id没有被绑定校验密码
        //校验密码 BCrypt校验
        if(!passwordEncoder.matches(password,user1.getPassword())){
            return Result.fail("密码错误");
        }
        //密码正确
        user1.setOpenid(openId);
        //更新user
        boolean update = update(user1, new UpdateWrapper<User>().eq("user_id",user1.getId()).set("open_id", openId));
        if(!update){
            return Result.fail(520,"绑定失败");
        }
        //user值导入userDTO
        UserDTO userDTO = new UserDTO();
        //手动导入不用工具类
        userDTO.setAvatar(user1.getIcon());
        userDTO.setPhoneNumber(user1.getPhone());
        userDTO.setRemark(user1.getRemark());
        userDTO.setUserId(user1.getId());
        userDTO.setNickName(user1.getNickName());
        return Result.ok("操作成功",userDTO);
    }

    private User getByIdWithCache(Long id) {
        return cacheClient.queryWithPassThrough(
                USER_KEY,
                id,
                User.class,
                this::getById,
                CACHE_TTL,
                TimeUnit.MINUTES
        );
    }

    @Override
    public Result queryUserById(Long id) {
        User user = this.getByIdWithCache(id);
        //判断用户是否存在
        if(user==null){
            return Result.fail("用户不存在");
        }
        UserDTO userDTO = new UserDTO(user.getId(),user.getNickName(),user.getIcon(),user.getPhone(),user.getRemark());
        return Result.ok("获取成功",userDTO);
    }

    @Override
    public Result userUpdateById(User user) {
        log.info("更新用户信息:{}",user);
        UserDTO userDTO = UserHolder.getUser();
        long userId = userDTO.getUserId();
        user.setId(userId);
        boolean success = userMapper.updateUser(user,userId);
        if(!success){
            return Result.fail("更新失败");
        }
        //删除缓存
        stringRedisTemplate.delete(USER_KEY+userId);
        User newUser = this.getByIdWithCache(userId);
        userDTO = new UserDTO(newUser.getId(),newUser.getNickName(),newUser.getIcon(),newUser.getPhone(),newUser.getIcon());
        return Result.ok("更新成功",userDTO);
    }

    @Override
    public Result queryUserByKeyword(String keyword) {

        // 调用 Mapper 方法，执行分页查询

        List<UserDTO> list = userMapper.getUserList(keyword);
        UserList userList = new UserList(list, (long) list.size());

        return Result.ok("获取成功", userList);
    }

    @Override
    public Result updatePassword(PassWordDTO passWordDTO) throws Exception {
        String newPassword = RsaUtil.decryptByPrivateKey(passWordDTO.getNewPassword(),PRIVATE_KEY);
        String oldPassword =  RsaUtil.decryptByPrivateKey(passWordDTO.getOldPassword(),PRIVATE_KEY);
        //获取用户信息
        UserDTO nowUser = UserHolder.getUser();
        //从数据库中获取密码
        User user = this.getByIdWithCache(nowUser.getUserId());
        String userPassword = user.getPassword();
        //判断旧密码是否正确
        if(!passwordEncoder.matches(oldPassword,userPassword)){
            return Result.fail(520,"旧密码错误");
        }
        boolean update = update(user, new UpdateWrapper<User>().eq("user_id",user.getId()).set("password", passwordEncoder.encode(newPassword)));
        if(!update){
            return Result.fail(520,"修改失败");
        }
        stringRedisTemplate.delete(USER_KEY+nowUser.getUserId());
        return Result.ok("操作成功", (Object) "修改成功");
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
