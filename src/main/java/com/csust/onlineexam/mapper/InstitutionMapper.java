package com.csust.onlineexam.mapper;

import com.csust.onlineexam.entity.InstitutionInfo;

import java.util.List;

/**
 * @author ：Lenovo.
 * @date ：Created in 14:31 2020/2/19
 */
public interface InstitutionMapper {

    /**
     * 获取学院专业信息
     * @param schoolCode 查询的学院编号
     * @return 学院的信息
     */
    public InstitutionInfo selectSchoolDepartmentInfoBySchoolCode(Integer schoolCode);

    /**
     * 获取所有的学院代码
     * @return 所有的机构代码
     */
    public List<Integer> getAllSchoolCode();
}
