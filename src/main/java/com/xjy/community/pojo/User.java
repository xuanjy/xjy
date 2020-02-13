package com.xjy.community.pojo;

import lombok.Data;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/7 16:31
 */
@Data
public class User {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
