package com.ideal.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2020/5/28 16:49
 * 陈银杰
 */
@NoArgsConstructor
@Data
@Alias("answer")
@TableName("t_answer")
@Builder
@AllArgsConstructor
public class Answer implements Serializable,Cloneable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long questionId;

    private String loginCode;

    private String groupName;

    private String projectName;

    private String taskName;

    private String questionnaire;

    private String recoverDate;

    private String exportDate;

    private String question;

    private String answer;

    private String questionType;

    private String duration;

}
