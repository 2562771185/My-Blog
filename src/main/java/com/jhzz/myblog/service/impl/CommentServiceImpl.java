package com.jhzz.myblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhzz.myblog.common.Constant;
import com.jhzz.myblog.common.ResponseResult;
import com.jhzz.myblog.domain.Comment;
import com.jhzz.myblog.domain.SysUser;
import com.jhzz.myblog.domain.vo.CommentVo;
import com.jhzz.myblog.service.CommentService;
import com.jhzz.myblog.mapper.CommentMapper;
import com.jhzz.myblog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Huanzhi
 * @description 针对表【ms_comment】的数据库操作Service实现
 * @createDate 2022-08-01 16:45:15
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements CommentService {
    @Autowired
    private SysUserService userService;
    @Override
    public ResponseResult getCommentList(Long id, Long page) {
        Page<Comment> commentPage = new Page<>(page, Constant.DEFAULT_PAGE_SIZE);
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, id);
        Page<Comment> res = this.page(commentPage, queryWrapper);
        List<Comment> comments = res.getRecords();
        List<CommentVo> vos = BeanUtil.copyToList(comments, CommentVo.class);
        vos.forEach(comment -> {
            SysUser user = userService.getById(comment.getAuthorId());
            comment.setAuthorAvatar(user.getAvatar());
            comment.setAuthorNickname(user.getNickname());
        });
        long total = res.getTotal();
        long pages = res.getPages();
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("total",total);
        map.put("list",vos);
        map.put("pages",pages);
        return ResponseResult.okResult(map);
    }
}




