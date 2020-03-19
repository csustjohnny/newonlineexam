package com.csust.onlineexam.service;

import com.csust.onlineexam.entity.InstitutionInfo;

import java.util.List;

/**
 * @author Lenovo
 */
public interface IInstitutionService {

    /**
     * 获取所有的学院机构和班级信息
     * @return 所有机构信息
     */
    public List<InstitutionInfo> getAllInstitutionInfo();
}
