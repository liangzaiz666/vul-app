package com.lz.vulapp.mapper;

import com.lz.vulapp.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author liangzai
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-07-22 15:15:34
* @Entity com.lz.vulapp.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User findOneByUsername(@Param("username") String username);
}




