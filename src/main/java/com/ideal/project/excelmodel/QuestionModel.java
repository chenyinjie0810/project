package com.ideal.project.excelmodel;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2020/5/27 15:00
 * 陈银杰
 */
@Data
@ToString
public class QuestionModel implements Serializable {
    /**
     * 序号
     */
    private String sortNumber;
    /**
     * 题干
     */
    private String question;
    /**
     * 类型
     */
    private String type;
    /**
     * 答案A
     */
    private String answerA;
    /**
     * 答案B
     */
    private String answerB;
    /**
     * 答案C
     */
    private String answerC;
    /**
     * 答案D
     */
    private String answerD;
    /**
     * 答案E
     */
    private String answerE;
    /**
     * 答案F
     */
    private String answerF;
    /**
     * 答案G
     */
    private String answerG;
    /**
     * 答案H
     */
    private String answerH;
    /**
     * 答案I
     */
    private String answerI;
}
