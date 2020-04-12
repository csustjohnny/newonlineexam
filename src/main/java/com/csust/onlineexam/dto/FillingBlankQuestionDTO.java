package com.csust.onlineexam.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：Lenovo
 * @date ：Created in 2020/4/12 9:20
 * @description : 填空题传输转换类
 * @modified By：
 */
@Data
public class FillingBlankQuestionDTO {
    private Integer questionId;

    private String createTeacher;

    private Integer subjectSubordinate;

    private String question;

    private String blankOne;

    private String blankTwo;

    private String blankThree;

    private String blankFour;

    private String blankFive;

    private String knowledgePoint;

    private Integer level;

    private Integer blankCount;

    private String subjectName;
}
