package com.xjy.community.controller;

import com.xjy.community.dto.CommentDTO;
import com.xjy.community.dto.QuestionDTO;
import com.xjy.community.enums.CommentTypeEnum;
import com.xjy.community.pojo.Question;
import com.xjy.community.service.CommentService;
import com.xjy.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/13 14:25
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id, Model model) {
        QuestionDTO questionDTO = questionService.getById(id);

        List<Question> relatedQuestions = questionService.selectRelated(questionDTO);

        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        // 累加阅读数
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }
}
