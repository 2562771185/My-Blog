package com.jhzz.myblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jhzz.myblog.common.ResponseResult;
import com.jhzz.myblog.domain.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jhzz.myblog.domain.param.CommentParam;
import com.jhzz.myblog.domain.param.QueryParam;
import com.jhzz.myblog.domain.vo.ArticlePublishVo;
import com.jhzz.myblog.domain.vo.ArticleUpdateVo;
import com.jhzz.myblog.domain.vo.ArticleVo;

/**
* @author Huanzhi
* @description 针对表【ms_article】的数据库操作Service
* @createDate 2022-07-31 15:01:05
*/
public interface ArticleService extends IService<Article> {

    ResponseResult queryArticlePage(QueryParam queryParam);

    ArticleVo getArticlesById(Long id);

    ResponseResult editArticle(Long id);

    ResponseResult updateArticle(ArticleUpdateVo updateVo);

    ResponseResult delArticle(Long id);

    ResponseResult publish(ArticlePublishVo publishVo);

}
