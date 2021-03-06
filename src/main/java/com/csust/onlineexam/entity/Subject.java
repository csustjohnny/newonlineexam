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
 * @since 2020-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_subject")
@ApiModel(value="Subject对象", description="")
public class Subject extends Model<Subject> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "subject_id", type = IdType.AUTO)
    private Integer subjectId;

    private String courseName;


    @Override
    protected Serializable pkVal() {
        return this.subjectId;
    }

}
