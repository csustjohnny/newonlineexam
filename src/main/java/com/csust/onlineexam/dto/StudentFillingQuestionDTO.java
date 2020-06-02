package com.csust.onlineexam.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：Lenovo
 * @date ：Created in 2020/5/29 23:45
 * @description : 学生获取填空题
 * @modified By：
 */
@Data
public class StudentFillingQuestionDTO {
    private Integer questionId;

    private String question;

    private String blankOne;

    private String blankTwo;

    private String blankThree;

    private String blankFour;

    private String blankFive;

    private String knowledgePoint;

    private Integer level;

    private Integer blankCount;
}
