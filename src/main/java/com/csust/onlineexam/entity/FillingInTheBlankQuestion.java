package com.csust.onlineexam.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author johnNick
 * @since 2020-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_filling_in_the_blank_question")
@ApiModel(value="FillingInTheBlankQuestion对象", description="")
public class FillingInTheBlankQuestion extends Model<FillingInTheBlankQuestion> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "question_id", type = IdType.AUTO)
    private Integer questionId;

    private String createTeacher;

    private Integer subjectSubordinate;

    private String question;

    private String blankOne;

    private String blankTwo;

    private String blankThree;

    private String blankFour;

    private String blankFive;

    @ApiModelProperty(value = "多个知识点用空格隔开 ")
    private String knowledgePoint;

    private Integer level;

    private Integer blankCount;


    @Override
    protected Serializable pkVal() {
        return this.questionId;
    }

}
