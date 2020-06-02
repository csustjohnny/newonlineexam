package com.csust.onlineexam.dto;

import lombok.Data;

/**
 * @author ：Lenovo
 * @date ：Created in 2020/5/30 10:25
 * @description : 学生获取编程题类
 * @modified By：
 */
@Data
public class StudentCodeQuestionDTO {
    private Integer id;

    private String title;

    private String description;

    private String input;

    private String output;

    private String sampleInput;

    private String sampleOutput;

    private String knowledgePoint;

    private Integer level;

    private String hint;

    private Integer timeLimit;

    private Integer memoryLimit;
}
