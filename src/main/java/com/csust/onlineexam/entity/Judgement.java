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
 * @since 2020-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_judgement")
@ApiModel(value="Judgement对象", description="")
public class Judgement extends Model<Judgement> {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String title;

    private Boolean answer;

    private String knowledgePoint;

    private Integer level;

    private String createTeacher;

    private Integer subjectSubordinate;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
