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
import java.util.Date;


@NoArgsConstructor
@Data
@Alias("question")
@TableName("t_question")
@Builder
@AllArgsConstructor
public class Question implements Serializable,Cloneable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String dayNumber;

    private Integer sortNumber;

    private String question;

    private String questionDesc;

    private String questionType;

    private String answer;

    private Date createDate;

    private Date modifyDate;


}