alter table t_curse drop foreign key FK_course_subject_fk;
alter table t_judgement drop foreign key FK_judgementQuestion_subject_fk;
alter table t_choicequestion drop foreign key FK_choiceQuestion_subject_fk;
alter table t_filling_in_the_blank_question drop foreign key FK_fillingBlankQuestion_subject_fk;
alter table t_subject modify subject_id int auto_increment;
alter table t_choiceQuestion add constraint FK_choiceQuestion_subject_fk foreign key (subject_subordinate)
    references t_subject (subject_id) on delete restrict on update restrict;
alter table t_filling_in_the_blank_question add constraint FK_fillingBlankQuestion_subject_fk foreign key (subject_subordinate)
    references t_subject (subject_id) on delete restrict on update restrict;
alter table t_judgement add constraint FK_judgementQuestion_subject_fk foreign key (subject_subordinate)
    references t_subject (subject_id) on delete restrict on update restrict;
alter table t_curse add constraint FK_course_subject_fk foreign key (subject_id)
    references t_subject (subject_id) on delete restrict on update restrict;
rename table t_curse to t_course;