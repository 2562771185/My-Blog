package com.jhzz.myblog.controller;

import com.jhzz.myblog.aop.LogAnnotation;
import com.jhzz.myblog.common.AppHttpCodeEnum;
import com.jhzz.myblog.common.ResponseResult;
import com.jhzz.myblog.service.LoginService;
import com.jhzz.myblog.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/8/4
 * \* Time: 9:40
 * \* Description:
 * \
 */
@RestController
@Slf4j
@RequestMapping("oss")
public class OSSController {
    @Resource
    private OssService ossService;
    @Autowired
    private LoginService loginService;

    @PostMapping("/upload")
    @LogAnnotation(module = "OSS", operation = "上传图片")
    public ResponseResult uploadFile(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String token,
             @RequestParam("host") String host) {
        ResponseResult result = loginService.checkToken(token);
        if (!result.getCode().equals(200)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH, AppHttpCodeEnum.NO_OPERATOR_AUTH.getMsg());
        }
        log.info("file:{}", file.getOriginalFilename());
        String url = ossService.uploadFile(file,host);
        return ResponseResult.okResult(url);
    }

    @PostMapping("/uploadlist")
    public ResponseResult uploadFileList(@RequestParam("file") MultipartFile file, @RequestParam("host") String host) {
        log.info("file:{}", file.getOriginalFilename());
        String url = ossService.uploadFile(file,host);
        return ResponseResult.okResult(url);
    }

    @PostMapping("/delete")
    public ResponseResult deleteFile(@RequestParam("filename") String filename, @RequestHeader("Authorization") String token) {
        ResponseResult result = loginService.checkToken(token);
        if (!result.getCode().equals(200)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH, AppHttpCodeEnum.NO_OPERATOR_AUTH.getMsg());
        }
        log.info("filename:{}", filename);
        ossService.deleteFile(filename);
        return ResponseResult.okResult(200, "未保存，删除图片");
    }

}
