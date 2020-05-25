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
 * @since 2020-05-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_code_question")
@ApiModel(value="CodeQuestion对象", description="")
public class CodeQuestion extends Model<CodeQuestion> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String description;

    private String input;

    private String output;

    private String sampleInput;

    private String sampleOutput;

    private String testInput;

    private String testOutput;

    private String knowledgePoint;

    private Integer level;

    private String hint;

    private String createTeacher;

    private Integer timeLimit;

    private Integer memoryLimit;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
