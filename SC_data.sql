use SelectCourse;
insert student values('201726202021','付卫平','男',21,'计信学院','1231');
insert student values('201726202022','罗凯威','男',20,'计信学院','1232');
insert student values('201726202024','华志毅','男',19,'计信学院','1233');
insert student values('201726202005','焦翀','男',20,'体育学院','1234');
insert student values('201726202002','李文俊','男',20,'计信学院','1235');
insert student values('201726202030','杨腾','男',20,'计信学院','1236');
insert student values('201726202018','邹春晖','男',20,'计信学院','1236');


insert teacher values('t0001','杨庆红','123','计信学院','13555555555');
insert teacher values('t0002','周勇','123','计信学院','14755555555');
insert teacher values('t0003','漆志群','123','计信学院','13655555555');
insert teacher values('t0004','周琪云','123','计信学院','18355555555');
insert teacher values('t0010','蔡徐坤','123','体育学院','15555555555');
insert teacher values('t0020','周杰伦','123','音乐学院','15355555555');
insert teacher values('t0030','王羲之','123','美术学院','13355555555');


insert course values('c0001','数据结构',4,'计信学院');
insert course values('c0002','C语言',3,'计信学院');
insert course values('c0003','计算机组成原理',4,'计信学院');
insert course values('c0004','算法分析与设计',3,'计信学院');
insert course values('c0005','编译原理',5,'计信学院');
insert course values('c0010','篮球',1,'体育学院');
insert course values('c0020','音乐赏析',2,'音乐学院');
insert course values('c0030','硬笔书法',1,'美术学院');

insert sc values('201726202021','c0001','t0001',null);
insert sc values('201726202021','c0002','t0002',93);
insert sc values('201726202021','c0004','t0003',94);
insert sc values('201726202022','c0001','t0003',91);
insert sc values('201726202022','c0002','t0002',100);
insert sc values('201726202022','c0003','t0004',98);
insert sc values('201726202024','c0002','t0002',90);
insert sc values('201726202024','c0003','t0004',90);
insert sc values('201726202024','c0004','t0003',88);
insert sc values('201726202002','c0001','t0001',99);
insert sc values('201726202002','c0004','t0001',100);
insert sc values('201726202002','c0005','t0001',89);
insert sc values('201726202030','c0001','t0003',98);
insert sc values('201726202030','c0002','t0002',97);
insert sc values('201726202030','c0003','t0004',85);
insert sc values('201726202030','c0004','t0001',91);
insert sc values('201726202018','c0001','t0003',90);
insert sc values('201726202018','c0002','t0002',94);
insert sc values('201726202018','c0003','t0004',92);
insert sc values('201726202018','c0004','t0003',93);

insert st values('t0001','c0001',null);
insert st values('t0001','c0004',null);
insert st values('t0001','c0005',null);
insert st values('t0002','c0002',null);
insert st values('t0003','c0001',null);
insert st values('t0003','c0004',null);
insert st values('t0004','c0003',null);
insert st values('t0010','c0010',null);
insert st values('t0020','c0020',null);
insert st values('t0030','c0030',null);

select * from student;
select * from teacher;
select * from course;
select * from sc;
select * from st;