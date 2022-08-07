package com.jhzz.myblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhzz.myblog.common.ResponseResult;
import com.jhzz.myblog.domain.Comment;
import com.jhzz.myblog.domain.param.CommentParam;
import com.jhzz.myblog.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("get")
    public ResponseResult getCommentList(@RequestParam("id")Long id,@RequestParam("page")Long page){
        return commentService.getCommentList(id, page);

    }
    @ApiOperation("评论文章")
    @PostMapping("/add")
    public ResponseResult comment(@RequestBody CommentParam commentParam) {
        return commentService.comment(commentParam);
    }

    @ApiOperation("删除评论")
    @PostMapping("/delete")
    public ResponseResult deleteComment(@RequestBody CommentParam commentParam) {
        return commentService.deleteComment(commentParam);
    }

}
