package com.xjy.community.mapper;

import com.xjy.community.dto.QuestionQueryDTO;
import com.xjy.community.pojo.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);

    int incCommentCount(Question record);

    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}