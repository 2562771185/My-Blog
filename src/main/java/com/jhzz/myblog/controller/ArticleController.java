package com.jhzz.myblog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.jhzz.myblog.common.ResponseResult;
import com.jhzz.myblog.domain.Article;
import com.jhzz.myblog.domain.param.QueryParam;
import com.jhzz.myblog.domain.vo.ArticleEditVo;
import com.jhzz.myblog.domain.vo.ArticlePublishVo;
import com.jhzz.myblog.domain.vo.ArticleUpdateVo;
import com.jhzz.myblog.domain.vo.ArticleVo;
import com.jhzz.myblog.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/7/31
 * \* Time: 15:03
 * \* Description:
 * \
 */
@Api
@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ApiOperation("获取文章分页")
    @PostMapping("/post")
    public ResponseResult queryArticlePage(@RequestBody QueryParam queryParam) {
        return articleService.queryArticlePage(queryParam);
    }
    @ApiOperation("根据id获取文章")
    @GetMapping("/get")
    public ResponseResult getArticleById(@RequestParam("id")Long id) {
        ArticleVo vo = articleService.getArticlesById(id);
        return ResponseResult.okResult(vo);
    }
    @ApiOperation("根据id编辑文章")
    @GetMapping("/edit")
    public ResponseResult editArticle(@RequestParam("id")Long id) {
        return articleService.editArticle(id);
    }
    @ApiOperation("更新文章")
    @PostMapping("/update")
    public ResponseResult updateArticle(ArticleUpdateVo updateVo) {
        return articleService.updateArticle(updateVo);
    }
    @GetMapping("/del")
    public ResponseResult delArticle(@RequestParam("id")Long id) {
        return articleService.delArticle(id);
    }
    @PostMapping("/publish")
    public ResponseResult publish(ArticlePublishVo publishVo) {
        return articleService.publish(publishVo);
    }
}
