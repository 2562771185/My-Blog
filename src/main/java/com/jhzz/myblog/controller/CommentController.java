package com.jhzz.myblog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhzz.myblog.common.ResponseResult;
import com.jhzz.myblog.domain.Comment;
import com.jhzz.myblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("get")
    public ResponseResult getCommentList(@RequestParam("id")Long id,@RequestParam("page")Long page){
        return commentService.getCommentList(id, page);

    }

}
