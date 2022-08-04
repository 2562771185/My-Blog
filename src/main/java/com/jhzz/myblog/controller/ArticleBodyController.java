package com.jhzz.myblog.controller;

import com.jhzz.myblog.common.AppHttpCodeEnum;
import com.jhzz.myblog.common.ResponseResult;
import com.jhzz.myblog.domain.ArticleBody;
import com.jhzz.myblog.service.ArticleBodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/7/31
 * \* Time: 23:28
 * \* Description:
 * \
 */
@RestController
@RequestMapping("body")
public class ArticleBodyController {
    @Autowired
    private ArticleBodyService articleBodyService;
    @GetMapping("get")
    public ResponseResult getArticleBodyById(@RequestParam("id")Long id) {
        ArticleBody body = articleBodyService.getById(id);
        if (body == null){
            return ResponseResult.errorResult(400,"文章不存在");
        }
        return ResponseResult.okResult(body);
    }
}
