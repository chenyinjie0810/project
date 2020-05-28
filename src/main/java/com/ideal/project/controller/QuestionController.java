package com.ideal.project.controller;

import com.ideal.project.async.QuestionAsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

/**
 * @Author chenyj
 * @Description
 * @Date create by 2020/5/27 10:22
 * 陈银杰
 */
@RestController
public class QuestionController {

    @Autowired
    private QuestionAsync questionAsync;

    @GetMapping("/test")
    public String test() throws FileNotFoundException {
//        questionAsync.resolveQuestion();
        questionAsync.saveAnswer();
        return "成功";
    }

}
