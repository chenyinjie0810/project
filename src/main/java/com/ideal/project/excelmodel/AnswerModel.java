package com.ideal.project.excelmodel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.ToString;
import java.io.Serializable;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2020/5/28 16:15
 * 陈银杰
 */
@Data
@ToString
public class AnswerModel implements Serializable {

    @ExcelProperty(value = "登录码")
    private String loginCode;

    @ExcelProperty(value = "用户组名称")
    private String groupName;

    @ExcelProperty(value = "项目名称")
    private String projectName;

    @ExcelProperty(value = "任务名称")
    private String taskName;

    @ExcelProperty(value = "问卷名称")
    private String questionnaire;

    @ExcelProperty(value = "问卷回收时间")
    private String recoverDate;

    @ExcelProperty(value = "问卷导出时间")
    private String exportDate;

    @ExcelProperty(value = "题干")
    private String question;

    @ExcelProperty(value = "答案")
    private String answer;

    @ExcelProperty(value = "题目分类名称")
    private String questionType;

    @ExcelProperty(value = "答题时长")
    private String duration;


}
