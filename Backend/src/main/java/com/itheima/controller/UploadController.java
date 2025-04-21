package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * ClassName:UploadController
 * Package:com.itheima.controller
 * Description:
 *
 * @date:2025-04-05 5:40 p.m.
 * @author:Qss
 */

@Slf4j
@RestController
public class UploadController {

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    /**
     * 往aliyun存储
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        log.info("文件上传：{}",file.getOriginalFilename());
//        将文件交给oss存储管理
        String url = aliyunOSSOperator.upload(file.getBytes(),file.getOriginalFilename());
        log.info("文件上传oss：url：{}", url);
//        最终文件上传时需要返回文件的访问路径(json里的data)
        return Result.success(url);
    }
    /**
     * 往本地磁盘存储
     * @param name
     * @param age
     * @param file
     * @return
     * @throws IOException
     */
//    @PostMapping("/upload")
//    public Result upload(String name, Integer age, MultipartFile file) throws IOException {
//        log.info("文件上传：{},{},{}",name,age,file);
////        获取原始文件名
//        String originalFilename = file.getOriginalFilename();
//
////        新的文件名
//        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//        String newFileName = UUID.randomUUID().toString() + "." + extension;
//
////        保存文件
//        file.transferTo(new File("/Users/tanshushu/Desktop/Web-ai-code/web-project/images" + newFileName));
//        return Result.success();
//    }
}
