package com.jhzz.myblog.domain.param;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/7/31
 * \* Time: 21:56
 * \* Description:
 * \
 */
@Data
public class QueryParam {
    private Long page;
    private String account;
    private String search;

    public boolean checkAllParams() {
        return StrUtil.isAllNotBlank(search,account) && page != null;
    }
}
