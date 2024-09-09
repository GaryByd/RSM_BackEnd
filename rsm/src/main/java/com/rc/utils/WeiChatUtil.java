package com.rc.utils;



import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;


public class WeiChatUtil {
    private static final String appid = "wx49b29dd4bcd70d0b";

    private static final String secret = "9fd7b88e7399987a4c278933de7776e1";

    public static String getSessionId(String code) {

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code";
        String replaceUrl = url.replace("{0}", appid).replace("{1}", secret).replace("{2}", code);
        System.out.println(("发送链接为：" + replaceUrl));
        String res = HttpUtil.get(replaceUrl);
        System.out.println("发送链接后获得的数据"+res);


        // 解析 JSON 字符串
        JSONObject jsonObject = JSONUtil.parseObj(res);
        // 获取 openid 字段
        String openid = jsonObject.getStr("openid");
        // 打印结果
        System.out.println("openid: " + openid);
        return openid;
    }
}
