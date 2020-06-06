package com.csust.onlineexam.dto;

import lombok.Data;

/**
 * @author ：Lenovo
 * @date ：Created in 2020/6/5 21:09
 * @description : 自动生成试卷参数
 * @modified By：
 */
@Data
public class AutoGenerateTestPaperDTO {
    private Integer examId;
    private Integer choiceQuestionCount;
    private Integer scorePerChoiceQuestion;
    private Integer fillingQuestionCount;
    private Integer scorePerFillingQuestion;
    private Integer judgementQuestionCount;
    private Integer scorePerJudgementQuestion;
    private Integer codeQuestionCount;
    private Integer scorePerCodeQuestion;
    private Integer subjectId;
}
