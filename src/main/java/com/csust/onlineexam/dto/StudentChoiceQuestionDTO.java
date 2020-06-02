package com.csust.onlineexam.dto;

import lombok.Data;

/**
 * @author ：Lenovo
 * @date ：Created in 2020/5/29 12:46
 * @description : 学生获取选择题数据转换类
 * @modified By：
 */
@Data
public class StudentChoiceQuestionDTO {
    private Integer id;

    private String title;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private String optionE;

    private String optionF;

    private String answer;
}
