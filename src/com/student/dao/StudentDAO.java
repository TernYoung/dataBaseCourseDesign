package com.student.dao;

import java.sql.SQLException;
import com.student.base.BaseDAO;

/**
 * @Description: Data Access Object of Student
 * @ClassName: StudentDAO
 * 
 */
public class StudentDAO extends BaseDAO {

    private static StudentDAO sd = null;

    public static synchronized StudentDAO getInstance() {
        if (sd == null) {
            sd = new StudentDAO();
        }
        return sd;
    }

    public String queryForLogin(String username, String password) {
        String result = null;
        String sql = "select sno from student where sno=? and password=?";
        String[] param = {username,password};
        rs = db.executeQuery(sql, param);
        try {
            if (rs.next()) {
                result = rs.getString("sno");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            destory();
        }
        return result;
    }

    /**
     * 
     * @Description: query optional courses for a student.
     */
    public String[][] queryCourses(String sno) {
        String sql =
                "select * from course where cno not in (select cno from sc where sno=?)";
        String[] param = {sno};
        rs = db.executeQuery(sql, param);
        return buildResult();
    }

    /**
     * 
     * @Description: query selected courses for a student.
     */
    public String[][] querySelectedCourse(String sno) {
        String sql =
                "select cno,cname,credit,cdept,tname from selectedCourse where sno=?";
        String[] param = {sno};
        rs = db.executeQuery(sql, param);
        return buildResult();
    }
    /**
     * 
     * @Description: query selected teacher for a student.
     */
    public String[][] querySelecteTeacher(String cno) {
        String sql =
                "select * from couOfTea where cno=?";
        String[] param = {cno};
        rs = db.executeQuery(sql, param);
        return buildResult();
    }
    
    
    
    
    

    /**
     * 
     * @Description: query the grade of a specific student.
     */
    public String[][] queryStuGrade(String sno) {
        String sql =
                "select A.cno, cname, grade from course as A, sc as B where A.cno = B.cno and sno=? and grade is not null";
        String[] param = {sno};
        rs = db.executeQuery(sql, param);
        return buildResult();
    }


    /**
     * 
     * @throws CourseNotFoundException
     * @throws CourseNotSelectedException
     * @Description: query a student's grade of a teacher
     */
    public int queryCourseGrade(String sno,String cno,String tno)
            throws CourseNotFoundException, CourseNotSelectedException {
        String[][] teacher = queryTeacher(tno);
        if (teacher.length == 0) {
            throw new CourseNotFoundException();
        }
        String sql = "select grade from sc where sno=? and cno=? and tno=?";
        String[] param = {sno, cno,tno};
        rs = db.executeQuery(sql, param);
        String grade = null;
        try {
            if (rs.next()) {
                grade = rs.getString("grade");
            } else {
                throw new CourseNotSelectedException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            destory();
        }
        return Integer.parseInt(grade);
    }
    
    
    /**
     * 
     * @throws CourseNotFoundException0
     * @throws CourseNotSelectedException
     * @Description: query a student's grade of a course.
     */
    public int queryCourseGrade0(String sno,String cno)
            throws CourseNotFoundException, CourseNotSelectedException {
        String[][] course = queryCourse(cno);
        if (course.length == 0) {
            throw new CourseNotFoundException();
        }
        String sql = "select grade from sc where sno=? and cno=?";
        String[] param = {sno, cno};
        rs = db.executeQuery(sql, param);
        String grade = null;
        try {
            if (rs.next()) {
                grade = rs.getString("grade");
            } else {
                throw new CourseNotSelectedException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            destory();
        }
        return Integer.parseInt(grade);
    }

    /**
     *
     * @Description: select course for a student.<br>
     * (sno, cno) should be checked additionally! 
     * @see #queryCourseGrade(String, String)
     */
    public void selectCourse(String sno, String cno,String tno) {
        String sql = "insert into sc values (?,?,?,null)";
        String[] param = {sno, cno,tno};
        db.executeUpdate(sql, param);
    }

    /**
     *
     * @Description: drop course for a student.
     * (sno, cno) should be checked additionally! 
     * @see #queryCourseGrade(String, String)
     */
    public void dropCourse(String sno, String cno) {
        String sql = "delete from sc where sno=? and cno=?";
        String[] param = {sno, cno};
        db.executeUpdate(sql, param);
    }
}
