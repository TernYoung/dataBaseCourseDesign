package com.student.dao;

import java.sql.SQLException;

import com.student.base.BaseDAO;

/**
 * @Description: Data Access Object of Student
 * @ClassName: TeacherDAO
 * 
 */
public class TeacherDAO extends BaseDAO {

    private static TeacherDAO ad = null;

    public static synchronized TeacherDAO getInstance() {
        if (ad == null) {
            ad = new TeacherDAO();
        }
        return ad;
    }
    
    public String queryForLogin(String username, String password) {
        String result = null;
        String sql = "select tno from teacher where tno=? and password=?";
        String[] param = {username,password};
        rs = db.executeQuery(sql, param);
        try {
            if (rs.next()) {
                result = rs.getString("tno");
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
     * @Description: get all courses.
     */
    public String[][] getAllCourses(String tno) {
        String sql = "select * from couOfTea where tno =?";
        String[] param = {tno};
        rs = db.executeQuery(sql,param);
        return buildResult();
    }

    /**
     *
     * @Description: get all students.
     */
    public String[][] getAllStudents() {
        String sql = "select * from student";
        rs = db.executeQuery(sql);
        return buildResult();
    }
    
    /**
    *
    * @Description: get all teachers.
    */
   public String[][] getAllTeachers() {
       String sql = "select * from teacher";
       rs = db.executeQuery(sql);
       return buildResult();
   }


    /**
     *
     * @Description: query the course for a student.
     */
    public String[][] queryCourses(String sno) {
        String sql = "select cno from sc where sno=?";
        String[] param = {sno};
        rs = db.executeQuery(sql, param);
        return buildResult();
    }
    
    /**
    *
    * @Description: query the st for a teacher.
    */
   public String[][] queryST(String tno) {
       String sql = "select cno from st where tno=?";
       String[] param = {tno};
       rs = db.executeQuery(sql, param);
       return buildResult();
   }

    /**
     * 
     * @Description: update a student's grade.
     */
    public int updateCourseGrade(String sno, String cno, String grade) {
        String sql = "update sc set grade=? where sno=? and cno=?";
        String[] prarm = {grade, sno, cno};
        return db.executeUpdate(sql, prarm);
    }
    
   
}
