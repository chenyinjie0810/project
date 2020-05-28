package com.ideal.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ideal.project.entity.Question;

import java.util.List;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2020/5/27 10:17
 * 陈银杰
 */
public interface QuestionMapper extends BaseMapper<Question> {

    void insertQuqestionList(List<Question> questionList);

    int deleteByPrimaryKey(Long id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);
}

