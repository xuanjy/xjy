package com.xjy.community.pojo;

import lombok.Data;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/9 14:56
 */
@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
}
