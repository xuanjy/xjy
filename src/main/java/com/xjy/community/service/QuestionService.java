package com.xjy.community.service;

import com.xjy.community.dto.PageDTO;
import com.xjy.community.dto.QuestionDTO;
import com.xjy.community.dto.QuestionQueryDTO;
import com.xjy.community.exception.CustomizeErrorCode;
import com.xjy.community.exception.CustomizeException;
import com.xjy.community.mapper.QuestionExtMapper;
import com.xjy.community.mapper.QuestionMapper;
import com.xjy.community.mapper.UserMapper;
import com.xjy.community.pojo.Question;
import com.xjy.community.pojo.QuestionExample;
import com.xjy.community.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 黄粱一梦
 * @Create on 2020/2/10 16:00
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PageDTO<QuestionDTO> list(String search, Integer page, Integer size) {

        if (StringUtils.isNotBlank(search)){
            String[] tags = StringUtils.split(search, " ");
            search= String.join("|", tags);
        }


        PageDTO pageDTO = new PageDTO<>();

        int totalPage;

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalCount;
//        if (search == null) {
//            totalCount = (int) (questionMapper.countByExample(new QuestionExample()));
//        } else {
            totalCount = questionExtMapper.countBySearch(questionQueryDTO);
//        }

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

        Integer offset = page < 1 ? 0 : size * (page - 1);
        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setData(questionDTOList);
        return pageDTO;
    }

    public PageDTO<QuestionDTO> list(Long userId, Integer page, Integer size) {
        PageDTO<QuestionDTO> pageDTO = new PageDTO<>();

        int totalPage;

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        int totalCount = (int) questionMapper.countByExample(questionExample);

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
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setData(questionDTOList);
        return pageDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException("你找的问题不存在");
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            // 创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        } else {
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<Question> selectRelated(QuestionDTO queryDTO) {
        String tagString = queryDTO.getTag();
        if (StringUtils.isEmpty(tagString)){
            return new ArrayList<>();
        }
        String tag = queryDTO.getTag();
        tag = tag.replace('，',',').replace(",","|");
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(tag);
        return questionExtMapper.selectRelated(question);
    }
}