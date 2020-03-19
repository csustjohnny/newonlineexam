package com.csust.onlineexam.service.impl;

import com.csust.onlineexam.entity.InstitutionInfo;
import com.csust.onlineexam.mapper.InstitutionMapper;
import com.csust.onlineexam.service.IInstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lenovo
 */
@Service
public class InstitutionServiceImpl implements IInstitutionService {

    private final InstitutionMapper institutionMapper;

    @Autowired
    public InstitutionServiceImpl(InstitutionMapper institutionMapper) {
        this.institutionMapper = institutionMapper;
    }

    @Override
    public List<InstitutionInfo> getAllInstitutionInfo() {
        List<Integer> allSchoolCode = institutionMapper.getAllSchoolCode();
        List<InstitutionInfo> institutionList = new ArrayList<>();
        allSchoolCode.forEach(schoolCode->institutionList.add(institutionMapper.selectSchoolDepartmentInfoBySchoolCode(schoolCode)));
        return institutionList;
    }
}
