package com.xjy.community.dto;

import lombok.Data;

import java.util.Locale;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/6 17:17
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;

}
