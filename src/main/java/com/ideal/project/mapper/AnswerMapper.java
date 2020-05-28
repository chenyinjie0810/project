package com.ideal.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ideal.project.entity.Answer;

import java.util.List;

public interface AnswerMapper extends BaseMapper<Answer> {
    int deleteByPrimaryKey(Long id);

    int insert(Answer record);

    int insertSelective(Answer record);

    Answer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Answer record);

    int updateByPrimaryKey(Answer record);

    void insertAnswerList(List<Answer> answerList);
}