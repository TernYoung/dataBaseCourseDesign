package com.student.dao;

import com.student.base.BaseDAO;

/**
 * @Description: Data Access Object of Student
 * @ClassName: AdminDAO
 * 
 */
public class AdminDAO extends BaseDAO {

    private static AdminDAO ad = null;

    public static synchronized AdminDAO getInstance() {
        if (ad == null) {
            ad = new AdminDAO();
        }
        return ad;
    }

    /**
     * 
     * @Description: query students who have selected a specific course.
     */
    public String[][] queryStuWhoSeleCou(String cno) {
        String sql =
                "select sno,grade from course as A, sc as B where A.cno=B.cno and A.cno=?";
        String[] param = {cno};
        rs = db.executeQuery(sql, param);
        return buildResult();
    }

    /**
     *
     * @Description: get all courses.
     */
    public String[][] getAllCourses() {
        String sql = "select * from course";
        rs = db.executeQuery(sql);
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

    /**
     *
     * @throws CourseExistException
     * @Description: AddCourse
     */
    public void AddCourse(String[] prarm) throws CourseExistException {
        if (queryCourse(prarm[0]).length != 0) {
            // check if the course exist
            throw new CourseExistException();
        }
        String sql = "insert into course values(?,?,?,?,?)";
        db.executeUpdate(sql, prarm);
    }

    /**
     *
     * @throws CourseNotFoundException
     * @throws CourseSelectedException
     * @Description: DelCourse
     */
    public void DelCourse(String cno) throws CourseNotFoundException, CourseSelectedException {
        if (queryCourse(cno).length == 0) {
            // check if the course exist
            throw new CourseNotFoundException();
        }
        if (queryStuWhoSeleCou(cno).length != 0) {
            // check if some student selected the course
            throw new CourseSelectedException();
        }
        String sql = "delete from course where cno=?";
        String[] prarm = {cno};
        db.executeUpdate(sql, prarm);
    }

    /**
     *
     * @throws StudentExistException
     * @throws UserExistException
     * @Description: AddStudent
     */
    public void AddStudent(String[] prarm) throws StudentExistException{
        if (queryStudent(prarm[0]).length != 0) {
            // check if the student exist
            throw new StudentExistException();
        }
        String sql = "insert into student values(?,?,?,?,?,?)";
        prarm[5] = getSHA256(prarm[0] + prarm[5]);
        db.executeUpdate(sql, prarm);
    }

    /**
     *
     * @throws StudentNotFoundException
     * @throws StudentSelectedCourseException
     * @Description: DelStudent
     */
    public void DelStudent(String sno)
            throws StudentNotFoundException, StudentSelectedCourseException {
        if (queryStudent(sno).length == 0) {
            // check if the student exist
            throw new StudentNotFoundException();
        }
        if (queryCourses(sno).length != 0) {
            // check if the student selected some course
            throw new StudentSelectedCourseException();
        }
        String sql = "delete from student where sno=?";
        String[] prarm = {sno};
        db.executeUpdate(sql, prarm);
    }
    
    /**
    *
    * @throws TeacherExistException
    * @Description: AddTeacher
    */
   public void AddTeacher(String[] prarm) throws TeacherExistException{
       if (queryStudent(prarm[0]).length != 0) {
           // check if the student exist
           throw new TeacherExistException();
       }
//       if (queryUser(prarm[6]).length != 0) {
//           // check if the username exist
//           throw new UserExistException();
//       }
       String sql = "insert into teacher values(?,?,?,?,?)";
       prarm[2] = getSHA256(prarm[2] + prarm[0]);
       db.executeUpdate(sql, prarm);

   }

   /**
    *
    * @throws TeacherNotFoundException
    * @throws TeacherTeachCourseException
    * @Description: DelTeacher
    */
   public void DelTeacher(String tno)
           throws TeacherNotFoundException, TeacherTeachCourseException {
       if (queryTeacher(tno).length == 0) {
           // check if the student exist
           throw new TeacherNotFoundException();
       }
       if (queryST(tno).length != 0) {
           // check if the teacher teach some course
           throw new TeacherTeachCourseException();
       }
       String sql = "delete from teacher where tno=?";
       String[] prarm = {tno};
       db.executeUpdate(sql, prarm);
   }
}
