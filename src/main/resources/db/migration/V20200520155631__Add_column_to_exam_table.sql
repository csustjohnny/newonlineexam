alter table t_exam
    add create_teacher varchar(9) null;

alter table t_exam
    add constraint t_exam_t_teacher_teacher_no_fk
        foreign key (create_teacher) references t_teacher (teacher_no);

