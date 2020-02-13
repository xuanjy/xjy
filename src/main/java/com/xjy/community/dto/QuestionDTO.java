package com.xjy.community.dto;

import com.xjy.community.pojo.User;
import lombok.Data;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/10 15:59
 */
@Data
public class QuestionDTO {
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
    private User user;
}
