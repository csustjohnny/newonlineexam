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
 * @since 2020-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_school")
@ApiModel(value="School对象", description="")
public class School extends Model<School> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "school_code", type = IdType.AUTO)
    private Integer schoolCode;

    private String schoolName;


    @Override
    protected Serializable pkVal() {
        return this.schoolCode;
    }

}
