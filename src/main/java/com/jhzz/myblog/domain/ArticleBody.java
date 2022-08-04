package com.jhzz.myblog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 
 * @TableName ms_article_body
 */
@TableName(value ="ms_article_body")
@Data
public class ArticleBody implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 
     */
    private String content;

    /**
     * 
     */
    private String contentHtml;

    /**
     * 
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long articleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}