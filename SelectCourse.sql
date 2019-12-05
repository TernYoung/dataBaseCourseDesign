drop database SelectCourse;
create database SelectCourse;
use SelectCourse;
create table student(
	sno char(12) not null primary key,
    sname char(8) not null,
    sex enum('男','女'),
    age int(11),
    sdept char(10),
    password char(64) not null
);

create table teacher(
	tno char(5) not null  primary key,
    tname char(8) not null,
    password char(64) not null,
    cdept char(10) not null,
    tel char(11)
);

create table course(
	cno char(5) not null primary key,
    cname char(20) not null,
    credit int(11),
    cdept char(10)，
	tname char(8)
);

create table sc(
	sno char(12) not null,
    cno char(5) not null,
    tno char(5) not null,
    grade int(11),
    primary key(sno,cno,tno),
    constraint sc_sno foreign key(sno) references student(sno),
    constraint sc_cno foreign key(cno) references course(cno),
    constraint sc_tno foreign key(tno)  references teacher(tno)
    
);

create table st(
	tno char(5) not null,
    cno char(5) not null,
    numbers int,
    primary key(tno,cno),
    constraint st_tno foreign key(tno) references teacher(tno),
    constraint st_cno foreign key(cno) references course(cno)
);

show columns from student;
show columns from teacher;
show columns from course;
show columns from sc;
show columns from st;
