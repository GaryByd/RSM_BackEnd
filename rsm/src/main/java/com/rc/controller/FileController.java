package com.rc.controller;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.rc.domain.dto.Result;
import com.rc.utils.AliOssUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.DocFlavor;
import java.util.UUID;

import static com.rc.utils.AliOssUtil.BucketName;
import static com.rc.utils.AliOssUtil.Endpoint;

@RestController
@RequestMapping("/api/mp/oss")
public class FileController {
    @PostMapping(value = "/upload")
    public Result upload(MultipartFile file) throws Exception {
        //文件夹
        String folder = "rsm_weixin/";
        // 上传逻辑
        String originalFilename = file.getOriginalFilename();
        //保证文件的名字是唯一的
        String fileName = folder+UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        //file.transferTo(new File("A:\\桌面\\files\\"+fileName));
        String url = AliOssUtil.uploadFile(fileName,file.getInputStream());
        return Result.ok("上传成功", (Object) url);
    }



    @DeleteMapping(value = "/delete")
    public Result deleteFile(@RequestBody String url) throws Exception {
        // 构建 baseUrl
        String baseUrl = "https://" + BucketName + "." + Endpoint.substring(Endpoint.lastIndexOf("/") + 1) + "/";
        // 解析请求体中的 JSON
        JSON parse = JSONUtil.parse(url);
        String imgUrl = (String) parse.getByPath("url");
        // 移除 baseUrl，得到相对路径
        if (imgUrl != null && imgUrl.startsWith(baseUrl)) {
            imgUrl = imgUrl.replace(baseUrl, "");
        } else {
            return Result.fail("URL 无效或格式不正确");
        }
//        System.out.println(imgUrl);
        String deleteFileMsg = AliOssUtil.deleteFile(imgUrl);
        return Result.ok("操作成功", (Object) deleteFileMsg);
    }
}
