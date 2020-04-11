package com.csust.onlineexam.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.csust.onlineexam.entity.ChoiceQuestion;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：Lenovo
 * @date ：Created in 2020/4/10 18:12
 * @description : 选择题数据交换实体
 * @modified By：
 */
@Data
public class ChoiceQuestionDTO {
    private String subjectName;

    private Integer id;

    private String title;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private String optionE;

    private String optionF;

    private String answer;

    private String analysis;

    private String knowledgePoint;

    private Integer level;

    private String createTeacher;

    private Integer subjectSubordinate;

    private Boolean isMultiple;
}
