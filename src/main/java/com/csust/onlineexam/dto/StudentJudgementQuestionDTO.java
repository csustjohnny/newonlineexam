package com.csust.onlineexam.dto;

import lombok.Data;

/**
 * @author ：Lenovo
 * @date ：Created in 2020/5/29 23:53
 * @description : 学生获取判断题
 * @modified By：
 */
@Data
public class StudentJudgementQuestionDTO {
    private Integer id;

    private String title;

    private Boolean answer;
}
