package com.xjy.community.dto;

import com.xjy.community.pojo.User;
import lombok.Data;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/23 16:00
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private String typeName;
    private Long outerid;
    private Integer type;

}
