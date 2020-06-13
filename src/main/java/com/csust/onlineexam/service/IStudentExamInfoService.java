package com.csust.onlineexam.service;

import com.csust.onlineexam.entity.StudentExamInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author johnNick
 * @since 2020-06-06
 */
public interface IStudentExamInfoService extends IService<StudentExamInfo> {

    /**
     * 更新或添加答案信息
     * @param studentExamInfo 答案信息类
     * @return 更新或添加结果
     */
    boolean saveOrUpdateEntity(StudentExamInfo studentExamInfo) throws Exception;

    Integer getScoreByStudentExamId(String studentNo, Integer examId);
}
