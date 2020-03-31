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
 * @since 2020-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_subject")
@ApiModel(value="Subject对象", description="")
public class Subject extends Model<Subject> {

    private static final long serialVersionUID = 1L;

    private Integer subjectId;

    private String curseName;


    @Override
    protected Serializable pkVal() {
        return this.subjectId;
    }

}
