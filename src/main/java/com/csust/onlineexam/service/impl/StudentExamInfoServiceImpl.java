package com.csust.onlineexam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csust.onlineexam.entity.StudentExamInfo;
import com.csust.onlineexam.mapper.ChoiceQuestionMapper;
import com.csust.onlineexam.mapper.FillingInTheBlankQuestionMapper;
import com.csust.onlineexam.mapper.JudgementMapper;
import com.csust.onlineexam.mapper.StudentExamInfoMapper;
import com.csust.onlineexam.service.IStudentExamInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author johnNick
 * @since 2020-06-06
 */
@Service
public class StudentExamInfoServiceImpl extends ServiceImpl<StudentExamInfoMapper, StudentExamInfo> implements IStudentExamInfoService {

    final ChoiceQuestionMapper choiceQuestionMapper;
    final JudgementMapper judgementMapper;
    final FillingInTheBlankQuestionMapper fillingInTheBlankQuestionMapper;

    @Autowired
    public StudentExamInfoServiceImpl(ChoiceQuestionMapper choiceQuestionMapper, JudgementMapper judgementMapper, FillingInTheBlankQuestionMapper fillingInTheBlankQuestionMapper) {
        this.choiceQuestionMapper = choiceQuestionMapper;
        this.judgementMapper = judgementMapper;
        this.fillingInTheBlankQuestionMapper = fillingInTheBlankQuestionMapper;
    }

    @Override
    public boolean saveOrUpdateEntity(StudentExamInfo studentExamInfo) throws Exception {
        QueryWrapper<StudentExamInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_no",studentExamInfo.getStudentNo())
                .eq("exam_id",studentExamInfo.getExamId())
                .eq("question_id",studentExamInfo.getQuestionId())
                .eq("question_type",studentExamInfo.getQuestionType());
        if(studentExamInfo.getAnswer()==null){
            studentExamInfo.setAnswer("");
        }
        if(this.getBaseMapper().selectMaps(queryWrapper).size()>0){
            //更新
            throw new Exception("您已考试完毕");
//            return this.getBaseMapper().update(studentExamInfo,queryWrapper)>0;
        } else {
            //新增
            return this.getBaseMapper().insert(studentExamInfo)>0;
        }
    }

    @Override
    public Integer getScoreByStudentExamId(String studentNo, Integer examId) {
        int sumScore = 0;
        //获取选择题总分
        Integer choiceScore = choiceQuestionMapper.getScoreByExamIdStudent(studentNo,examId);
        if(choiceScore!=null){
            sumScore += choiceScore;
        }
        //获取判断题总分
        Integer judgementScore = judgementMapper.getScoreByExamIdStudent(studentNo,examId);
        if(judgementScore !=null){
            sumScore += judgementScore;
        }
        //获取填空题总分
        Integer fillingBlankScore = fillingInTheBlankQuestionMapper.getScoreByExamIdStudent(studentNo,examId);
        if(fillingBlankScore != null){
            sumScore += fillingBlankScore;
        }
        return sumScore;
    }
}
