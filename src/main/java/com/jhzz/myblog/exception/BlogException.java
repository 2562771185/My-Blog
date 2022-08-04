package com.jhzz.myblog.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/5/19
 * \* Time: 14:41
 * \* Description:
 * \
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogException extends RuntimeException{

    private Integer code;

    private String msg;
}
