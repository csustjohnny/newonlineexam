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
 * @since 2020-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_test_paper")
@ApiModel(value="TestPaper对象", description="")
public class TestPaper extends Model<TestPaper> {

    private static final long serialVersionUID = 1L;

    private Integer examId;

    private String questionType;

    private Integer questionId;

    private Integer questionOrder;


    @Override
    protected Serializable pkVal() {
        return this.examId;
    }

}
