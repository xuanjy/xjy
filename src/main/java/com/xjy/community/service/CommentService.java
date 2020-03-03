package com.xjy.community.service;

import com.xjy.community.dto.CommentDTO;
import com.xjy.community.enums.CommentTypeEnum;
import com.xjy.community.enums.NotificationTypeEnum;
import com.xjy.community.enums.NotificationStatusEnum;
import com.xjy.community.exception.CustomizeErrorCode;
import com.xjy.community.exception.CustomizeException;
import com.xjy.community.mapper.*;
import com.xjy.community.pojo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/17 13:13
 */
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private NotificationMapper notificationMapper;


    @Transactional
    public void insert(Comment comment, User commentator) {
        if (comment.getParentid() == null || comment.getParentid() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentid());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

            //回复问题
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentid());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insertSelective(comment);

            // 增加评论数
            dbComment.setId(comment.getParentid());
            dbComment.setCommentCount(1);
            commentExtMapper.incCommentCount(dbComment);

            //创建通知
            createNotify(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());

        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentid());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insertSelective(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);

            //创建通知
            createNotify(comment, question.getCreator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, question.getId());
        }
    }

    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId) {
        if (receiver.equals(comment.getCommentator())) {
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterid(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insertSelective(notification);
    }

    // 回复列表
    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentidEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");

        // 根据问题id获取所有评论
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        // 流处理获取去重的问题创建者
        Set<Long> commentators = comments.stream().map(Comment::getCommentator).collect(Collectors.toSet());

        // 获取评论人并转换为Map
        List<Long> userIds = new ArrayList<>(commentators);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);

        // 获取评论人并转换为Map
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));

        // 转换comment为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
