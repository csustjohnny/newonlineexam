package com.csust.onlineexam.entity;

import lombok.Data;

import java.util.List;

/**
 * @author ：Lenovo.
 * @date ：Created in 13:59 2020/2/19
 */
@Data
public class InstitutionInfo {
    private School school;
    private List<Department> departmentInfoList;
}
