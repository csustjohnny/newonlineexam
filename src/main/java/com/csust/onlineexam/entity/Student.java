package com.csust.onlineexam.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;

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
@TableName("t_student")
@ApiModel(value="Student对象", description="")
public class Student extends Model<Student> {

    private static final long serialVersionUID = 1L;

    @TableId
    @Size(max = 10)
    private String studentNo;

    private String name;

    private String sex;

    private String phone;

    private Integer classId;

    private String qq;

    private String wechat;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @Override
    protected Serializable pkVal() {
        return this.studentNo;
    }

}
