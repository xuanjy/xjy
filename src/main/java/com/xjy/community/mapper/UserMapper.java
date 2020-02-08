package com.xjy.community.mapper;

import com.xjy.community.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/7 16:29
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user (name,accountId,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
