package com.jhzz.myblog.controller;

import com.jhzz.myblog.common.ResponseResult;
import com.jhzz.myblog.service.OssService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
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
@Api
@RequestMapping("oss")
public class OSSController {
    @Resource
    private OssService ossService;

    @PostMapping("/upload")
    public ResponseResult uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("file:{}",file.getOriginalFilename());
        String url = ossService.uploadFile(file);
        return ResponseResult.okResult(url);
    }
    @PostMapping("/uploadlist")
    public ResponseResult uploadFileList(@RequestParam("file[]") MultipartFile file) {
        log.info("file:{}",file.getOriginalFilename());
        String url = ossService.uploadFile(file);
        return ResponseResult.okResult(url);
    }
    @PostMapping("/delete")
    public ResponseResult deleteFile(@RequestParam("filename") String filename) {
        log.info("filename:{}",filename);
        ossService.deleteFile(filename);
        return ResponseResult.okResult(200,"未保存，删除图片");
    }

}
