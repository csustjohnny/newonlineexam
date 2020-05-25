package com.csust.onlineexam.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
@TableName("t_exam")
@ApiModel(value="Exam对象", description="")
public class Exam extends Model<Exam> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "exam_id", type = IdType.AUTO)
    private Integer examId;

    private String examTitle;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Boolean isVisible;

    private String examClass;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;

    @ApiModelProperty(value = "限制IP地址一般用匹配方式，一般用192.28.3.*,192.18.6.*,等形式")
    private String ipLimit;

    private String createTeacher;


    @Override
    protected Serializable pkVal() {
        return this.examId;
    }

}
