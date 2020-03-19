package com.csust.onlineexam.entity;

import lombok.Data;

import java.util.List;

/**
 * @author ：Lenovo.
 * @date ：Created in 14:11 2020/3/18
 */
@Data
public class DepartmentInfo {
    private Department department;
    private List<ClassInfo> classInfoList;
}
