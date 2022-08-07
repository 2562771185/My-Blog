package com.jhzz.myblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhzz.myblog.common.ResponseResult;
import com.jhzz.myblog.domain.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jhzz.myblog.domain.param.CommentParam;

import java.util.List;

/**
* @author Huanzhi
* @description 针对表【ms_comment】的数据库操作Service
* @createDate 2022-08-01 16:45:15
*/
public interface CommentService extends IService<Comment> {

    ResponseResult getCommentList(Long id, Long page);

    ResponseResult comment(CommentParam commentParam);

    ResponseResult deleteComment(CommentParam commentParam);
}
