package com.jhzz.myblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhzz.myblog.aop.LogAnnotation;
import com.jhzz.myblog.common.AppHttpCodeEnum;
import com.jhzz.myblog.common.ResponseResult;
import com.jhzz.myblog.domain.Comment;
import com.jhzz.myblog.domain.param.CommentParam;
import com.jhzz.myblog.service.CommentService;
import com.jhzz.myblog.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/8/1
 * \* Time: 16:52
 * \* Description:
 * \
 */
@Api
@RestController
@RequestMapping("comment")
@Slf4j
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private LoginService loginService;
    @GetMapping("get")
    public ResponseResult getCommentList(@RequestParam("id")Long id,@RequestParam("page")Long page){
        return commentService.getCommentList(id, page);

    }
    @ApiOperation("评论文章")
    @PostMapping("/add")
    @LogAnnotation(module = "评论模块",operation = "新增评论")
    public ResponseResult comment(@RequestBody CommentParam commentParam,@RequestHeader("Authorization") String token) {
        ResponseResult result = loginService.checkToken(token);
        if (!result.getCode().equals(AppHttpCodeEnum.SUCCESS)){
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH,AppHttpCodeEnum.NO_OPERATOR_AUTH.getMsg());
        }
        return commentService.comment(commentParam);
    }

    @ApiOperation("删除评论")
    @PostMapping("/delete")
    public ResponseResult deleteComment(@RequestBody CommentParam commentParam,@RequestHeader("Authorization") String token) {
        ResponseResult result = loginService.checkToken(token);
        if (!result.getCode().equals(AppHttpCodeEnum.SUCCESS)){
            return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH,AppHttpCodeEnum.NO_OPERATOR_AUTH.getMsg());
        }
        return commentService.deleteComment(commentParam);
    }

}
