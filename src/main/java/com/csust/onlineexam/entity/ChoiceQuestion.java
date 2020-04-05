package com.csust.onlineexam.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2020-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_choice_question")
@ApiModel(value="ChoiceQuestion对象", description="")
public class ChoiceQuestion extends Model<ChoiceQuestion> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "题目描述")
    private String title;

    @TableField("option_A")
    private String optionA;

    @TableField("option_B")
    private String optionB;

    @TableField("option_C")
    private String optionC;

    @TableField("option_D")
    private String optionD;

    @TableField("option_E")
    private String optionE;

    @TableField("option_F")
    private String optionF;

    @ApiModelProperty(value = "如果是多选，答案用空格隔开")
    private String answer;

    private String analysis;

    private String knowledgePoint;

    @ApiModelProperty(value = "难度指数")
    private Integer level;

    private String createTeacher;

    private Integer subjectSubordinate;

    private Boolean isMultiple;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
