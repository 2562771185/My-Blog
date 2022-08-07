package com.jhzz.myblog.mapper;

import com.jhzz.myblog.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Huanzhi
* @description 针对表【ms_sys_user】的数据库操作Mapper
* @createDate 2022-07-31 18:31:49
* @Entity com.jhzz.myblog.domain.SysUser
*/
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}




