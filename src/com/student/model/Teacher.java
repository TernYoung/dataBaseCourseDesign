package com.student.model;

/**
 * @Description: Student
 * @ClassName: Student
 * 
 */
public class Teacher {

    private String tno;
    private String tname;
    private String tdept;
    private String tel;
    @Deprecated
    private String password;

    public Teacher(String sno) {
        this.tno = sno;
    }

    public String getTno() {
        return tno;
    }

    public void setTno(String tno) {
        this.tno = tno;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTdept() {
        return tdept;
    }

    public void setTdept(String tdept) {
        this.tdept = tdept;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Deprecated
    public String getPassword() {
        return password;
    }

    @Deprecated
    public void setPassword(String password) {
        this.password = password;
    }

}
