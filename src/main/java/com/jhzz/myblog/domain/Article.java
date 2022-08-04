package com.jhzz.myblog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 
 * @TableName ms_article
 */
@TableName(value ="ms_article")
@Data
public class Article implements Serializable {
    /**
     * 
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.AUTO)
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}