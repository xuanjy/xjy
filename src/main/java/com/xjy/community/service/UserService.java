package com.xjy.community.service;

import com.xjy.community.mapper.UserMapper;
import com.xjy.community.pojo.User;
import com.xjy.community.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/14 12:50
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountidEqualTo(user.getAccountid());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            User dbUser = users.get(0);
            User updateUser = new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
        }
    }
}
