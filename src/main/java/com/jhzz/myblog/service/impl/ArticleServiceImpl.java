package com.jhzz.myblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhzz.myblog.common.AppHttpCodeEnum;
import com.jhzz.myblog.common.Constant;
import com.jhzz.myblog.common.ResponseResult;
import com.jhzz.myblog.domain.Article;
import com.jhzz.myblog.domain.ArticleBody;
import com.jhzz.myblog.domain.SysUser;
import com.jhzz.myblog.domain.param.CommentParam;
import com.jhzz.myblog.domain.param.QueryParam;
import com.jhzz.myblog.domain.vo.ArticleEditVo;
import com.jhzz.myblog.domain.vo.ArticlePublishVo;
import com.jhzz.myblog.domain.vo.ArticleUpdateVo;
import com.jhzz.myblog.domain.vo.ArticleVo;
import com.jhzz.myblog.exception.BlogException;
import com.jhzz.myblog.service.ArticleBodyService;
import com.jhzz.myblog.service.ArticleService;
import com.jhzz.myblog.mapper.ArticleMapper;
import com.jhzz.myblog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author Huanzhi
 * @description 针对表【ms_article】的数据库操作Service实现
 * @createDate 2022-07-31 15:01:05
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
        implements ArticleService {
    @Autowired
    private SysUserService userService;
    @Autowired
    private ArticleBodyService articleBodyService;

    @Override
    public ResponseResult queryArticlePage(QueryParam queryParam) {
        Page<Article> articlePage = new Page<>(queryParam.getPage(), Constant.DEFAULT_PAGE_SIZE);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getGmtCreate);
        SysUser sysUser = userService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getAccount, queryParam.getAccount()));
        if (sysUser != null) {
            queryWrapper.eq(Article::getAuthorId, sysUser.getId());
        }
        if (StrUtil.isNotBlank(queryParam.getSearch())) {
            queryWrapper.like(Article::getTitle, queryParam.getSearch())
                    .or()
                    .like(Article::getSummary, queryParam.getSearch());
        }
        Page<Article> resPage = this.page(articlePage, queryWrapper);
        List<Article> articles = resPage.getRecords();
        List<ArticleVo> articleVos = BeanUtil.copyToList(articles, ArticleVo.class);
        articleVos.forEach(item -> {
            Long authorId = item.getAuthorId();
            SysUser user = userService.getById(authorId);
            if (!Objects.isNull(user)){
                item.setAuthorAccount(user.getAccount());
                item.setAuthorCover(user.getAvatar());
                item.setAuthorNickname(user.getNickname());
            }
        });
        long total = resPage.getTotal();
        long pages = resPage.getPages();
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("total", total);
        map.put("list", articleVos);
        map.put("pages", pages);
        return ResponseResult.okResult(map);
    }

    @Override
    public ArticleVo getArticlesById(Long id) {
        Article article = this.getById(id);
        ArticleVo vo = BeanUtil.copyProperties(article, ArticleVo.class);
        SysUser user = userService.getById(vo.getAuthorId());
        vo.setAuthorAccount(user.getAccount());
        vo.setAuthorCover(user.getAvatar());
        vo.setAuthorNickname(user.getNickname());
        return vo;
    }

    @Override
    public ResponseResult editArticle(Long id) {
        //1.查询文章主体
        ArticleBody body = articleBodyService.getOne(new LambdaQueryWrapper<ArticleBody>().eq(ArticleBody::getArticleId, id));

        //2.查询文章作者信息
        Article article = this.getById(id);
        if (ObjectUtil.isAllEmpty(body,article)){
            return ResponseResult.errorResult(500,"文章不存在");
        }
        SysUser author = userService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getId, article.getAuthorId()));
        ArticleEditVo vo = new ArticleEditVo();
        vo.setArticleBody(body);
        vo.setTitle(article.getTitle());
        vo.setArticleCover(article.getCover());
        vo.setAuthorAvatar(author.getAvatar());
        vo.setNickname(author.getNickname());
        vo.setAccount(author.getAccount());
        vo.setSummary(article.getSummary());
        return ResponseResult.okResult(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult updateArticle(ArticleUpdateVo updateVo) {
        Article article = new Article();
        article.setId(updateVo.getId());
        article.setTitle(updateVo.getTitle());
        if (StrUtil.isNotBlank(updateVo.getCover())) {
            article.setCover(updateVo.getCover());
        }
        if (StrUtil.isNotBlank(updateVo.getSummary())) {
            article.setSummary(updateVo.getSummary());
        }
        //更新文章
        this.updateById(article);
        //更新主体
        ArticleBody body = new ArticleBody();
        body.setId(updateVo.getBodyId());
        body.setContent(updateVo.getContent());
        articleBodyService.updateById(body);

        return ResponseResult.okResult(article.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult delArticle(Long id) {

        Article article = this.getById(id);
        Long bodyId = article.getBodyId();
        //删除文章主体
        articleBodyService.removeById(bodyId);
        //删除文章本体
        //todo 删除评论
        this.removeById(id);
        return ResponseResult.okResult(200, "删除文章成功！");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseResult publish(ArticlePublishVo publishVo) {
        String account = publishVo.getAccount();
        SysUser user = userService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getAccount, account));
        if (user == null) {
            throw new BlogException(AppHttpCodeEnum.NOT_USER.getCode(), AppHttpCodeEnum.NOT_USER.getMsg());
        }
        Article article = new Article();
        article.setCommentCounts(0);
        article.setGmtCreate(new Date());
        article.setSummary(publishVo.getSummary());
        article.setTitle(publishVo.getTitle());
        article.setViewCounts(0);
        article.setWeight(0);
        article.setAuthorId(user.getId());
        article.setCategoryId(0);
        article.setGmtModified(new Date());
        article.setCover(publishVo.getCover());
        this.save(article);
        //文章主体
        ArticleBody body = new ArticleBody();
        body.setContent(publishVo.getContent());
        body.setContentHtml("");
        body.setArticleId(article.getId());
        articleBodyService.save(body);
        //设置文章主体信息
        Article articleDone = new Article();
        articleDone.setBodyId(body.getId());
        articleDone.setId(body.getArticleId());
        this.updateById(articleDone);
        return ResponseResult.okResult(article.getId());
    }


}




