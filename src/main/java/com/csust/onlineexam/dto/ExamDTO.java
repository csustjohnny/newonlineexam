package com.csust.onlineexam.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ：Lenovo
 * @date ：Created in 2020/5/20 21:11
 * @description : exam DTO
 * @modified By：
 */
@Data
public class ExamDTO {
    private Integer examId;

    private String examTitle;

    private String startTime;

    private String endTime;

    private Boolean isVisible;
}
