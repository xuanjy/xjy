package com.xjy.community.mapper;

import com.xjy.community.pojo.Comment;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/21 14:03
 */
public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}
