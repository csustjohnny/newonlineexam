package com.csust.onlineexam.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@TableName("t_teacher")
@ApiModel(value="Teacher对象", description="")
public class Teacher extends Model<Teacher> {

    private static final long serialVersionUID = 1L;

    @TableId
    private String teacherNo;

    private String teacherName;

    private String phone;

    private String email;

    @JsonIgnore
    private String password;

    private String qq;

    private String wechat;


    @Override
    protected Serializable pkVal() {
        return this.teacherNo;
    }

}
