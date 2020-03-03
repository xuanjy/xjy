package com.xjy.community.enums;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/23 15:41
 */
public enum NotificationStatusEnum {
    UNREAD(0), READ(1)
    ;
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
