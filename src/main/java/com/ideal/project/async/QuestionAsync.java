package com.ideal.project.async;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.ideal.project.entity.Answer;
import com.ideal.project.entity.Question;
import com.ideal.project.excelmodel.AnswerModel;
import com.ideal.project.mapper.AnswerMapper;
import com.ideal.project.mapper.QuestionMapper;
import com.ideal.project.excelmodel.QuestionModel;
import com.ideal.project.util.EasyExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2020/5/27 11:01
 * 陈银杰
 */
@Component
public class QuestionAsync {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private AnswerMapper answerMapper;

    /**
     * @desc: 解析问题于答案
     * @author: chenyj
     * @date: 2020/5/27
     */
    @Async("taskExecutor")
    @Transactional(rollbackFor = Exception.class)
    public void resolveQuestion() throws FileNotFoundException {
        InputStream inputStream=new FileInputStream("E:\\上海理想文档\\Normal_Cohort\\DG\\DGS questionnaire1.xlsx");
        List<QuestionModel> lists = EasyExcelUtil.readExcelWithModelBySheet(inputStream, QuestionModel.class, new ReadSheet(2));
        Assert.notNull(lists,"表格为空");
        //问卷入库list
        List<Question> questionList=new ArrayList<>(lists.size());
        String dayNumber = null;
        String questionDesc=null;
        for (QuestionModel questionModel: lists){
            if (!questionModel.getSortNumber().matches("-?[0-9]+.*[0-9]*")){
                dayNumber =questionModel.getSortNumber();
                questionDesc=questionModel.getQuestion();
                continue;
            }else {
                Question question= Question.builder()
                        .sortNumber(Integer.parseInt(questionModel.getSortNumber()))
                        .question(questionModel.getQuestion())
                        .questionDesc(questionDesc)
                        .dayNumber(dayNumber)
                        .questionType(questionModel.getType())
                        .build();
                if (!StringUtils.isEmpty(questionModel.getAnswerA())){
                    StringBuffer answer= new StringBuffer();
                    answer.append(questionModel.getAnswerA());
                    this.answerAppend(answer,questionModel.getAnswerB());
                    this.answerAppend(answer,questionModel.getAnswerC());
                    this.answerAppend(answer,questionModel.getAnswerD());
                    this.answerAppend(answer,questionModel.getAnswerE());
                    this.answerAppend(answer,questionModel.getAnswerF());
                    this.answerAppend(answer,questionModel.getAnswerG());
                    this.answerAppend(answer,questionModel.getAnswerH());
                    this.answerAppend(answer,questionModel.getAnswerI());
                    question.setAnswer(answer.toString());
                }
                questionList.add(question);
            }
        }
        if (!questionList.isEmpty()){
//            questionMapper.insertQuqestionList(questionList);
        }
    }

    /**
     * @desc: 解析答案
     * @author: chenyj
     * @date: 2020/5/28
     * @throws FileNotFoundException
     */
    @Transactional(rollbackFor = Exception.class)
    @Async("taskExecutor")
    public void saveAnswer() throws FileNotFoundException {
        // 读取答案
        InputStream inputStreamData=new FileInputStream("E:\\上海理想文档\\Normal_Cohort\\DG\\DGS questionnaire data.xlsx");
        List<AnswerModel> answerModelList = EasyExcelUtil.readExcelWithModelBySheet(inputStreamData, AnswerModel.class,new ReadSheet(0));
        List<Answer> answerList=new ArrayList<>(answerModelList.size());
        String loginCode=null, groupName=null,projectName=null, taskName=null,
                questionnaire=null, recoverDate=null, exportDate=null;
        for (AnswerModel answerModel:answerModelList){
            if (!StringUtils.isEmpty(answerModel.getLoginCode())){
                loginCode=answerModel.getLoginCode();
                groupName=answerModel.getGroupName();
                projectName=answerModel.getProjectName();
                taskName=answerModel.getTaskName();
                questionnaire=answerModel.getQuestionnaire();
                recoverDate=answerModel.getRecoverDate();
                exportDate=answerModel.getExportDate();
            }
            Answer answer=Answer.builder()
                    .loginCode(loginCode)
                    .groupName(groupName)
                    .projectName(projectName)
                    .taskName(taskName)
                    .questionnaire(questionnaire)
                    .recoverDate(recoverDate)
                    .exportDate(exportDate).build();
            Map<String,Object> map=new HashMap<>(8);
            map.put("day_number",questionnaire);
            map.put("question",answerModel.getQuestion());
            List<Question> questionList = questionMapper.selectByMap(map);
            if (questionList!=null&&!questionList.isEmpty()){
                answer.setQuestionId(questionList.get(0).getId());
                answer.setQuestion(answerModel.getQuestion());
                answer.setAnswer(answerModel.getAnswer());
                answer.setQuestionType(answerModel.getQuestionType());
                answer.setDuration(answerModel.getDuration());
                answerList.add(answer);
            }
        }
        if (!answerList.isEmpty()){
            answerMapper.insertAnswerList(answerList);
        }
    }

    /**
     * @desc: 拼接答案
     * @author: chenyj
     * @date: 2020/5/28
     * @param sbf
     * @param answer
     */
    private void answerAppend(StringBuffer sbf, String answer){
        if (!StringUtils.isEmpty(answer)){
            sbf.append(","+answer);
        }
    }
}
