alter table t_code_question
    add time_limit int null;

alter table t_code_question
    add memory_limit int null;
alter table t_code_question modify id int auto_increment;
