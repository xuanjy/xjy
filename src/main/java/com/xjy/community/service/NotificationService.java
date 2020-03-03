package com.xjy.community.service;

import com.xjy.community.dto.NotificationDTO;
import com.xjy.community.dto.PageDTO;
import com.xjy.community.enums.NotificationStatusEnum;
import com.xjy.community.enums.NotificationTypeEnum;
import com.xjy.community.exception.CustomizeErrorCode;
import com.xjy.community.exception.CustomizeException;
import com.xjy.community.mapper.NotificationMapper;
import com.xjy.community.pojo.Notification;
import com.xjy.community.pojo.NotificationExample;
import com.xjy.community.pojo.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/23 16:01
 */
@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    public PageDTO<NotificationDTO> list(Long userId, Integer page, Integer size) {
        PageDTO<NotificationDTO> pageDTO = new PageDTO<>();

        int totalPage;

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        int totalCount = (int) notificationMapper.countByExample(notificationExample);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        pageDTO.setPagination(totalPage, page);

        int offset = size * (page - 1);
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        if (notifications.size() == 0) {
            return pageDTO;
        }

        List<NotificationDTO> notificationDTOList = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOList.add(notificationDTO);
        }
        pageDTO.setData(notificationDTOList);
        return pageDTO;
    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        notificationMapper.selectByExample(notificationExample);
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        if (!notification.getReceiver().equals(user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
