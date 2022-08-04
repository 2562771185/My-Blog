package com.jhzz.myblog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/7/31
 * \* Time: 14:57
 * \* Description:
 * \
 */
@RestController
public class HelloController {
    @GetMapping("hello")
    public String hello() {
        return "这是我的一个项目";
    }
}
