package com.ideal.project.job;

import com.ideal.project.async.QuestionAsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2020/5/28 18:50
 * 陈银杰
 */

@Component
public class ResolveJob {
    @Autowired
    private QuestionAsync questionAsync;

    /**
     * @desc: 每天凌晨2点执行
     * @author: chenyj
     * @date: 2020/5/28
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void test(){
        try {
//            questionAsync.resolveQuestion();
//            questionAsync.saveAnswer();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
