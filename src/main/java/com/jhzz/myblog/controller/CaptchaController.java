package com.jhzz.myblog.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.google.code.kaptcha.Producer;
import com.jhzz.myblog.common.AjaxResult;
import com.jhzz.myblog.common.Constant;
import com.jhzz.myblog.common.ResponseResult;
import com.jhzz.myblog.util.IdUtils;
import com.jhzz.myblog.util.RedisCache;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/8/1
 * \* Time: 17:56
 * \* Description:
 * \
 */
@RestController
@Slf4j
@Api
public class CaptchaController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;


    @Resource
    private RedisCache redisCache;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public AjaxResult getCode(HttpServletResponse response) throws IOException {
        AjaxResult ajax = AjaxResult.success();
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constant.CAPTCHA_CODE_KEY + uuid;
        String capStr = null, code = null;
        BufferedImage image = null;
        // 生成验证码
        capStr = code = captchaProducer.createText();
        image = captchaProducer.createImage(capStr);
        log.info("生成验证码("+new DateTime()+"):"+code);
        redisCache.setCacheObject(verifyKey, code, Constant.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }
    @PostMapping("/delCaptcha")
    public AjaxResult delCaptcha(@RequestParam("uuid") String uuid){
        log.info("key:{}",Constant.CAPTCHA_CODE_KEY + uuid);
        String code = redisCache.getCacheObject(Constant.CAPTCHA_CODE_KEY + uuid);
        log.info("被刷新的验证码：{}",code);
        if (StrUtil.isNotBlank(code)){
            boolean deleteObject = redisCache.deleteObject(Constant.CAPTCHA_CODE_KEY + uuid);
            if (deleteObject){
                return AjaxResult.success("删除验证码成功！");
            }
        }
        return AjaxResult.error("删除验证码失败");
    }
}
