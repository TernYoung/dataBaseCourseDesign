use SelectCourse;
-- 视图:已选（未结课）课程
-- （学号,课程号,课程名，学分，开课院系,任课老师)
drop view if exists selectedcourse;
create view selectedCourse
as
select sc.sno,course.cno,course.cname,course.credit,course.cdept,teacher.tname
from course,sc,teacher
where course.cno = sc.cno and teacher.tno = sc.tno and sc.grade is NULL;
-- group by sc.sno;
-- 测试
select cno,cname,credit,cdept,tname 
from selectedCourse;
-- where sno=?;

-- 选老师视图
-- 课程号，课程名，教工号，教工名
drop view if exists couOfTea;
create view couOfTea
as
select course.cno,course.cname,st.tno,teacher.tname
from course,st,teacher
where course.cno=st.cno and teacher.tno = st.tno
order  by course.cno;
-- 测试
select * from couOfTea;
select * from selectedCourse;
