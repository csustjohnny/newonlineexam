package com.csust.onlineexam.entity;

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
 * @since 2020-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_exam2student")
@ApiModel(value="Exam2student对象", description="")
public class Exam2student extends Model<Exam2student> {

    private static final long serialVersionUID = 1L;

    private Integer examId;

    private String studentNo;


    @Override
    protected Serializable pkVal() {
        return this.examId;
    }

}
