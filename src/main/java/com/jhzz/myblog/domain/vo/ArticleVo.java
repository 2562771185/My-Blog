package com.jhzz.myblog.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/7/31
 * \* Time: 18:25
 * \* Description:
 * \
 */
@Data
public class ArticleVo implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 评论数量
     */
    private Integer commentCounts;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 简介
     */
    private String summary;

    /**
     * 标题
     */
    private String title;

    /**
     * 浏览数量
     */
    private Integer viewCounts;

    /**
     * 是否置顶
     */
    private Integer weight;

    /**
     * 作者id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long authorId;

    /**
     * 内容id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long bodyId;

    /**
     * 类别id
     */
    private Integer categoryId;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 封面图片地址
     */
    private String cover;

    private String authorNickname;
    private String authorAccount;
    private String authorCover;
    private static final long serialVersionUID = 1L;
}
