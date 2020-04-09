package com.csust.onlineexam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：Lenovo
 * @date ：Created in 2020/4/9 23:35
 * @description : 分页传输对象
 * @modified By：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDTO {
    private Integer code;
    private Object data;
    private Long count;
}
