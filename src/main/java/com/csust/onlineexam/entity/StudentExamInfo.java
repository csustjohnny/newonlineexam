package com.csust.onlineexam.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
 * @since 2020-06-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_student_exam_info")
@ApiModel(value="StudentExamInfo对象", description="")
public class StudentExamInfo extends Model<StudentExamInfo> {

    private static final long serialVersionUID = 1L;

    private String studentNo;

    private Integer examId;

    private Integer questionId;

    private String questionType;

    private String answer;


    @Override
    protected Serializable pkVal() {
        return this.studentNo;
    }

}
