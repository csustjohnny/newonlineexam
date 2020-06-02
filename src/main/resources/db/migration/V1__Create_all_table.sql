/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/5/26 15:42:10                           */
/*==============================================================*/


drop table if exists t_admin;

drop table if exists t_choice_question;

drop table if exists t_code_question;

drop table if exists t_course;

drop table if exists t_student_exam_info;

drop table if exists t_exam2student;

drop table if exists t_test_paper;

drop table if exists t_index;

drop table if exists t_exam;

drop table if exists t_filling_in_the_blank_question;

drop table if exists t_judgement;

drop table if exists t_subject;

drop table if exists t_student;

drop table if exists t_teacher_department;

drop table if exists t_class_info;

drop table if exists t_teacher;

drop table if exists t_department;

drop table if exists t_school;

/*==============================================================*/
/* Table: t_admin                                               */
/*==============================================================*/
create table t_admin
(
    admin_no             int(11) not null,
    admin_name           varchar(40),
    admin_password       varchar(255),
    primary key (admin_no)
);

/*==============================================================*/
/* Table: t_choice_question                                     */
/*==============================================================*/
create table t_choice_question
(
    id                   int not null auto_increment,
    title                text not null comment '题目描述',
    option_A             varchar(255),
    option_B             varchar(255),
    option_C             varchar(255),
    option_D             varchar(255),
    option_E             varchar(255),
    option_F             varchar(255),
    answer               char(11) not null comment '如果是多选，答案用空格隔开',
    analysis             text,
    knowledge_point      varchar(50),
    level                int comment '难度指数',
    create_teacher       varchar(9),
    subject_subordinate  int(11),
    is_multiple          bit not null,
    primary key (id)
);

/*==============================================================*/
/* Table: t_class_info                                          */
/*==============================================================*/
create table t_class_info
(
    class_id             int not null auto_increment,
    class_no             varchar(4) not null,
    class_name           varchar(20),
    department_id        int not null,
    teacher              varchar(10),
    primary key (class_id)
);

/*==============================================================*/
/* Table: t_code_question                                       */
/*==============================================================*/
create table t_code_question
(
    id                   int(11) not null,
    title                varchar(200) not null,
    description          text,
    input                text comment '输入说明',
    output               text comment '输出说明',
    memory_limit         int(11),
    time_limit           int(11),
    sample_input         text comment '样例输入',
    sample_output        text comment '样例输出',
    test_input           text comment '测试输入',
    test_output          text comment '测试输出',
    knowledge_point      varchar(50) comment '知识点',
    level                int,
    hint                 text comment '提示',
    create_teacher       varchar(9),
    primary key (id)
);

/*==============================================================*/
/* Table: t_course                                              */
/*==============================================================*/
create table t_course
(
    class_no             int not null,
    subject_id           int(11) not null,
    course_teacher       varchar(9),
    primary key (class_no, subject_id)
);

/*==============================================================*/
/* Table: t_department                                          */
/*==============================================================*/
create table t_department
(
    department_id        int not null auto_increment,
    school_code          int(11) not null,
    department_name      varchar(20),
    department_description varchar(100),
    primary key (department_id)
);

/*==============================================================*/
/* Table: t_exam                                                */
/*==============================================================*/
create table t_exam
(
    exam_id              int(11) not null auto_increment,
    exam_title           varchar(255),
    start_time           datetime,
    end_time             datetime,
    is_visible           boolean,
    exam_class           text,
    create_time          datetime,
    modify_time          datetime,
    ip_limit             varchar(50) comment '限制IP地址一般用匹配方式，一般用192.28.3.*,192.18.6.*,等形式',
    create_teacher       varchar(9),
    primary key (exam_id)
);

/*==============================================================*/
/* Table: t_exam2student                                        */
/*==============================================================*/
create table t_exam2student
(
    exam_id              int(11) not null,
    student_no           varchar(12) not null,
    primary key (exam_id, student_no)
);

/*==============================================================*/
/* Table: t_filling_in_the_blank_question                       */
/*==============================================================*/
create table t_filling_in_the_blank_question
(
    question_id          int(11) not null auto_increment,
    create_teacher       varchar(9),
    subject_subordinate  int(11),
    question             text not null,
    blank_one            varchar(100),
    blank_two            varchar(100),
    blank_three          varchar(100),
    blank_four           varchar(100),
    blank_five           varchar(100),
    knowledge_point      varchar(50) comment '多个知识点用空格隔开
            ',
    level                int,
    blank_count          int,
    primary key (question_id)
);

/*==============================================================*/
/* Table: t_index                                               */
/*==============================================================*/
create table t_index
(
    ID                   int not null auto_increment,
    index_description    text,
    index_name           varchar(20) not null,
    department           int not null,
    primary key (ID)
);

/*==============================================================*/
/* Table: t_judgement                                           */
/*==============================================================*/
create table t_judgement
(
    id                   int not null auto_increment,
    title                varchar(255) not null,
    answer               bit,
    knowledge_point      varchar(50),
    level                int,
    create_teacher       varchar(9),
    subject_subordinate  int(11),
    analysis             varchar(255),
    primary key (id)
);

/*==============================================================*/
/* Table: t_school                                              */
/*==============================================================*/
create table t_school
(
    school_code          int(11) not null auto_increment,
    school_name          varchar(20),
    primary key (school_code)
);

/*==============================================================*/
/* Table: t_student                                             */
/*==============================================================*/
create table t_student
(
    student_no           varchar(12) not null,
    name                 varchar(10),
    sex                  enum('男','女'),
    phone                char(11),
    class_id             int not null,
    qq                   varchar(11),
    wechat               varchar(25),
    password             varchar(100),
    primary key (student_no)
);

/*==============================================================*/
/* Table: t_student_exam_info                                   */
/*==============================================================*/
create table t_student_exam_info
(
    student_no           varchar(12) not null,
    exam_id              int(11) not null,
    question_id          int(11) not null,
    question_type        enum('选择题','判断题','填空题','编程题') not null,
    answer               text,
    primary key (student_no, exam_id, question_id, question_type)
);

/*==============================================================*/
/* Table: t_subject                                             */
/*==============================================================*/
create table t_subject
(
    subject_id           int(11) not null auto_increment,
    course_name          varchar(100),
    primary key (subject_id)
);

/*==============================================================*/
/* Table: t_teacher                                             */
/*==============================================================*/
create table t_teacher
(
    teacher_no           varchar(9) not null,
    teacher_name         varchar(10),
    phone                char(11),
    email                varchar(20),
    password             varchar(100),
    qq                   varchar(11),
    wechat               varchar(20),
    sex                  enum('男','女'),
    primary key (teacher_no)
);

/*==============================================================*/
/* Table: t_teacher_department                                  */
/*==============================================================*/
create table t_teacher_department
(
    department_id        int,
    teacher_no           varchar(9)
);

/*==============================================================*/
/* Table: t_test_paper                                          */
/*==============================================================*/
create table t_test_paper
(
    exam_id              int(11) not null,
    question_type        enum('choice_question','filling_in_the_blank_question','judgement_question','code_question') not null,
    question_id          int(11) not null,
    index_id             int,
    question_order       int,
    primary key (exam_id, question_type, question_id)
);

alter table t_choice_question add constraint FK_choiceOpt_teacher_fk foreign key (create_teacher)
    references t_teacher (teacher_no) on delete restrict on update cascade;

alter table t_choice_question add constraint FK_choiceQuestion_subject_fk foreign key (subject_subordinate)
    references t_subject (subject_id) on delete restrict on update restrict;

alter table t_class_info add constraint FK_class_depart_fk foreign key (department_id)
    references t_department (department_id) on delete restrict on update cascade;

alter table t_class_info add constraint FK_class_teacher_fk foreign key (teacher)
    references t_teacher (teacher_no) on delete set null on update cascade;

alter table t_code_question add constraint FK_codeQuestion_teacher_fk foreign key (create_teacher)
    references t_teacher (teacher_no) on delete restrict on update restrict;

alter table t_course add constraint FK_course_class_fk foreign key (class_no)
    references t_class_info (class_id) on delete restrict on update restrict;

alter table t_course add constraint FK_course_subject_fk foreign key (subject_id)
    references t_subject (subject_id) on delete restrict on update restrict;

alter table t_course add constraint FK_course_teacher_fk foreign key (course_teacher)
    references t_teacher (teacher_no) on delete restrict on update restrict;

alter table t_department add constraint FK_depart_school_fk foreign key (school_code)
    references t_school (school_code) on delete restrict on update cascade;

alter table t_exam add constraint FK_Reference_25 foreign key (create_teacher)
    references t_teacher (teacher_no) on delete restrict on update restrict;

alter table t_exam2student add constraint FK_exam2student_exam_fk foreign key (exam_id)
    references t_exam (exam_id) on delete restrict on update restrict;

alter table t_exam2student add constraint FK_exam2student_student_fk foreign key (student_no)
    references t_student (student_no) on delete restrict on update restrict;

alter table t_filling_in_the_blank_question add constraint FK_fillingBlankQuestion_subject_fk foreign key (subject_subordinate)
    references t_subject (subject_id) on delete restrict on update restrict;

alter table t_filling_in_the_blank_question add constraint FK_fillingQuestion_teacher_fk foreign key (create_teacher)
    references t_teacher (teacher_no) on delete restrict on update restrict;

alter table t_index add constraint FK_Reference_24 foreign key (department)
    references t_department (department_id) on delete restrict on update restrict;

alter table t_judgement add constraint FK_judgementQuestion_subject_fk foreign key (subject_subordinate)
    references t_subject (subject_id) on delete restrict on update restrict;

alter table t_judgement add constraint FK_judgement_teacher_fk foreign key (create_teacher)
    references t_teacher (teacher_no) on delete restrict on update restrict;

alter table t_student add constraint FK_student_class_fk foreign key (class_id)
    references t_class_info (class_id) on delete restrict on update cascade;

alter table t_student_exam_info add constraint FK_studentExamInfo_exam_fk foreign key (exam_id)
    references t_exam (exam_id) on delete restrict on update restrict;

alter table t_student_exam_info add constraint FK_studentExamInfo_student_fk foreign key (student_no)
    references t_student (student_no) on delete restrict on update restrict;

alter table t_teacher_department add constraint FK_Reference_21 foreign key (department_id)
    references t_department (department_id) on delete restrict on update restrict;

alter table t_teacher_department add constraint FK_Reference_22 foreign key (teacher_no)
    references t_teacher (teacher_no) on delete restrict on update restrict;

alter table t_test_paper add constraint FK_Reference_23 foreign key (index_id)
    references t_index (ID) on delete restrict on update restrict;

alter table t_test_paper add constraint FK_paper_exam_table foreign key (exam_id)
    references t_exam (exam_id) on delete restrict on update restrict;

