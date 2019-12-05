package com.student.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import com.student.AppConstants;
import com.student.base.BaseDAO.CourseNotFoundException;
import com.student.base.BaseDAO.CourseNotSelectedException;
import com.student.dao.StudentDAO;
import com.student.model.Student;

/**
 * @Description: Student Select Course View
 * @ClassName: StudentView
 * 
 */
public class StudentView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JFrame newwindow;
    private JPanel contentPane;
    private Student student;
    private JButton ensure;
    private String sno,cno,tno;
    private JTable infotable, coursetable, scoretable, selectedtable,teachertable;
    private static final String[] infocolumn = {AppConstants.SNO, AppConstants.SNAME,
            AppConstants.SEX, AppConstants.AGE, AppConstants.SDEPT};
    private static final String[] coursecolumn = {AppConstants.CNO, AppConstants.CNAME,
            AppConstants.CREDIT, AppConstants.CDEPT};
    private static final String[] unselectcoursecolumn = {AppConstants.CNO, AppConstants.CNAME,
            AppConstants.CREDIT, AppConstants.CDEPT, AppConstants.TNAME};
    private static final String[] scorecolumn =
            {AppConstants.CNO, AppConstants.CNAME, AppConstants.SCORE};
    private static final String[] selectteachercolumn =
        {AppConstants.CNO,AppConstants.CNAME, AppConstants.TNO,AppConstants.TNAME};
    private JTextField textField;
    private JTextField textField1;


    public StudentView(Student student) {
        this.student = student;
        System.out.println("Student " + student.getSno() + " Login Success.");

        setResizable(false);
        setTitle("学生选课");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("Student " + student.getSno() + " Logout.");
                new LoginView();
            }
        });

        setVisible(true);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        JPanel btnpanel = new JPanel();
        contentPane.add(btnpanel, BorderLayout.EAST);
        btnpanel.setLayout(new BoxLayout(btnpanel, BoxLayout.Y_AXIS));

        JButton selectbtn = new JButton("选课");
        selectbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton dropbtn = new JButton("退课");
        dropbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton closebtn = new JButton("关闭");
        closebtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        selectbtn.addActionListener(new SelectListener());
        dropbtn.addActionListener(new DropListener());
        closebtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnpanel.add(Box.createRigidArea(new Dimension(100, 50)));
        btnpanel.add(selectbtn);
        btnpanel.add(Box.createRigidArea(new Dimension(100, 50)));
        btnpanel.add(dropbtn);
        btnpanel.add(Box.createRigidArea(new Dimension(100, 50)));
        btnpanel.add(closebtn);

        JPanel centerpanel = new JPanel();
        contentPane.add(centerpanel, BorderLayout.CENTER);
        centerpanel.setLayout(new GridLayout(2, 2, 15, 15));

        initInfo(centerpanel);
        initCourse(centerpanel);
        initScore(centerpanel);
        initSelect(centerpanel);
        getRootPane().setDefaultButton(selectbtn);
    }

    private void initInfo(JPanel centerpanel) {
        System.err.println("Loading Student Info...");
        JPanel panel = new JPanel();
        centerpanel.add(panel);
        panel.setLayout(new BorderLayout());

        JPanel label = new JPanel();
        panel.add(label, BorderLayout.NORTH);
        label.add(new JLabel("学生详细信息"));

        infotable = new JTable();
        infotable.setEnabled(false);
        String[][] result = StudentDAO.getInstance().queryStudent(student.getSno());

        // Assign the information.
        student.setSname(result[0][1]);
        student.setSex(result[0][2]);
        try {
            // Maybe the age is NULL
            student.setAge(Integer.parseInt(result[0][3]));
        } catch (NumberFormatException e) {
            student.setAge(-1);
        }
        student.setSdept(result[0][4]);
        student.setUsername(result[0][5]);

        initTable(infotable, result, infocolumn);
        JScrollPane scrollpane = new JScrollPane(infotable);
        infotable.getTableHeader().setReorderingAllowed(false);
        panel.add(scrollpane, BorderLayout.CENTER);
    }

    private void initCourse(JPanel centerpanel) {
        System.err.println("Loading Course Info...");
        JPanel panel = new JPanel();
        centerpanel.add(panel);
        panel.setLayout(new BorderLayout());

        JPanel mainpanel = new JPanel();
        panel.add(mainpanel, BorderLayout.CENTER);
        mainpanel.setLayout(new BorderLayout());

        JPanel label = new JPanel();
        mainpanel.add(label, BorderLayout.NORTH);

        JLabel courselabel = new JLabel("可选课程");
        label.add(courselabel);

        coursetable = new JTable();
        coursetable.setEnabled(false);

        String[][] result = StudentDAO.getInstance().queryCourses(student.getSno());

        initTable(coursetable, result, coursecolumn);
        JScrollPane scrollPane = new JScrollPane(coursetable);
        coursetable.getTableHeader().setReorderingAllowed(false);
        mainpanel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputpanel = new JPanel();
        panel.add(inputpanel, BorderLayout.SOUTH);

        inputpanel.add(new JLabel(AppConstants.STUDENT_INPUT));
        textField = new JTextField();
        inputpanel.add(textField);
        textField.setColumns(10);
    }


    private void initScore(JPanel centerpanel) {
        System.err.println("Loading Score Info...");
        JPanel panel = new JPanel();
        centerpanel.add(panel);
        panel.setLayout(new BorderLayout());

        JPanel label = new JPanel();
        panel.add(label, BorderLayout.NORTH);
        label.add(new JLabel(AppConstants.STUDENT_SCORE));

        scoretable = new JTable();
        scoretable.setEnabled(false);
        String[][] result = StudentDAO.getInstance().queryStuGrade(student.getSno());

        initTable(scoretable, result, scorecolumn);
        JScrollPane scrollpane = new JScrollPane(scoretable);
        scoretable.getTableHeader().setReorderingAllowed(false);
        panel.add(scrollpane);

    }

    private void initSelect(JPanel centerpanel) {
        System.err.println("Loading Selected Info...");
        JPanel panel = new JPanel();
        centerpanel.add(panel);
        panel.setLayout(new BorderLayout());

        JPanel label = new JPanel();
        panel.add(label, BorderLayout.NORTH);
        label.add(new JLabel(AppConstants.STUDENT_SELECTED));

        selectedtable = new JTable();
        selectedtable.setEnabled(false);
        String[][] result = StudentDAO.getInstance().querySelectedCourse(student.getSno());

        initTable(selectedtable, result, unselectcoursecolumn);
        JScrollPane scrollpane = new JScrollPane(selectedtable);
        selectedtable.getTableHeader().setReorderingAllowed(false);
        panel.add(scrollpane);
    }

    private void initTable(JTable jTable, String[][] result, String[] column) {
        jTable.setModel(new DefaultTableModel(result, column));
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }
    
    private class SelectListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
        	String[][] result=null;
            cno = textField.getText();
            result = StudentDAO.getInstance().querySelecteTeacher(cno);
	        if (cno.equals("")) {
		        JOptionPane.showMessageDialog(null, AppConstants.CNO_NULL_ERROR);
		        return;
	        }
	        else {
	        	try {
	        		boolean flag=result[0][0].isEmpty();
	        	}catch(ArrayIndexOutOfBoundsException a) {
	        		JOptionPane.showMessageDialog(null, AppConstants.CNO_NOT_EXIST_ERROR);
	        		 return;
	        	}
	        	 if(true) {
	        		 textField1=new JTextField();
	                 ensure=new JButton("确定");
	                 teachertable=new JTable();
	                 teachertable.setEnabled(false);
	                 ensure.addActionListener(new ensureListener());
	                 newwindow=new JFrame("选择授课老师");
	                 newwindow.setLocation(100,50);
	                 newwindow.setSize(400,250);
	                 newwindow.setLocationRelativeTo(null);
	                 newwindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	                 newwindow.setLayout(new BorderLayout());
	                 
	                 initTable(teachertable, result, selectteachercolumn);
	                 JScrollPane scrollpane = new JScrollPane(teachertable);
	                 teachertable.getTableHeader().setReorderingAllowed(false);
	                 JPanel panellabel=new JPanel();
	                 panellabel.add(new JLabel("可选教师"));
	                 newwindow.add(panellabel,BorderLayout.NORTH);
	                 newwindow.add(scrollpane,BorderLayout.CENTER);
	                 
	                 JPanel panel = new JPanel();
	                 JPanel inputpanel = new JPanel();
	                 JLabel label=new JLabel("请输入教工号");
	                 inputpanel.add(label);
	                 textField1.setColumns(10);
	                 inputpanel.add(textField1);
	                 panel.add(inputpanel);
	                 panel.add(ensure);
	                 newwindow.add(panel,BorderLayout.SOUTH);
	                 newwindow.setVisible(true);
	        	 }
	        }
            
//            if (cno.equals("")) {
//                JOptionPane.showMessageDialog(null, AppConstants.CNO_NULL_ERROR);
//                return;
//            }
//            try {
//                StudentDAO.getInstance().queryCourseGrade(student.getSno(), cno);
//                JOptionPane.showMessageDialog(null, AppConstants.CNO_SELECTED_ERROR,
//                        AppConstants.ERROR, JOptionPane.ERROR_MESSAGE);
//            } catch (CourseNotFoundException e1) {
//                JOptionPane.showMessageDialog(null, AppConstants.CNO_NOT_EXIST_ERROR,
//                        AppConstants.ERROR, JOptionPane.ERROR_MESSAGE);
//            } catch (CourseNotSelectedException e2) {
//                StudentDAO.getInstance().selectCourse(student.getSno(), cno);
////                textField.setText(null);
////                updateTables();
////                System.out.println("Student " + student.getSno() + " selected course " + cno + ".");
//            } catch (NumberFormatException e3) {
//                JOptionPane.showMessageDialog(null, AppConstants.CNO_SELECTED_ERROR,
//                        AppConstants.ERROR, JOptionPane.ERROR_MESSAGE);
//            } catch (Exception e4) {
//                System.err.println("Unknown Error!");
//            }
        }

    }
    
    private class ensureListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	 sno=student.getSno();
        	 cno=textField.getText();
        	 tno = textField1.getText();
              if (tno.equals("")) {
              JOptionPane.showMessageDialog(null, "不存在该老师");
              return;
          }
          try {
              StudentDAO.getInstance().queryCourseGrade(sno, cno,tno);
              JOptionPane.showMessageDialog(null, "此老师已选",
                      AppConstants.ERROR, JOptionPane.ERROR_MESSAGE);
          } catch (CourseNotFoundException e1) {
              JOptionPane.showMessageDialog(null, AppConstants.CNO_NOT_EXIST_ERROR,
                      AppConstants.ERROR, JOptionPane.ERROR_MESSAGE);
          } catch (CourseNotSelectedException e2) {
              StudentDAO.getInstance().selectCourse(sno, cno, tno);
              textField.setText(null);
              updateTables();
              System.out.println("Student " + student.getSno() + " selected course " + cno + ".");
          } catch (NumberFormatException e3) {
              JOptionPane.showMessageDialog(null, AppConstants.CNO_SELECTED_ERROR,
                      AppConstants.ERROR, JOptionPane.ERROR_MESSAGE);
          } catch (Exception e4) {
              System.err.println("Unknown Error!");
          }
           newwindow.dispose();
        	
        	}
        }

    private class DropListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
        	 sno = student.getSno();
        	 //tno = textField1.getText();
             cno = textField.getText();
            if (cno.equals("")) {
                JOptionPane.showMessageDialog(null, AppConstants.CNO_NULL_ERROR);
                return;
            }
            try {
                StudentDAO.getInstance().queryCourseGrade0(sno,cno);
                JOptionPane.showMessageDialog(null, AppConstants.CNO_GRADED_ERROR,
                        AppConstants.ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (CourseNotFoundException e2) {
                JOptionPane.showMessageDialog(null, AppConstants.CNO_NOT_EXIST_ERROR,
                        AppConstants.ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (CourseNotSelectedException e2) {
                JOptionPane.showMessageDialog(null, AppConstants.CNO_NOT_SELECTED_ERROR,
                        AppConstants.ERROR, JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e2) {
                StudentDAO.getInstance().dropCourse(student.getSno(), cno);
                textField.setText(null);
                updateTables();
                System.out.println("Student " + student.getSno() + " droped course " + cno + ".");
            } catch (Exception e2) {
                System.err.println("Unknown Error!");
            }
        }
    }

    private void updateTables() {
        initTable(coursetable, StudentDAO.getInstance().queryCourses(student.getSno()),
                coursecolumn);
        initTable(selectedtable, StudentDAO.getInstance().querySelectedCourse(student.getSno()),
        		unselectcoursecolumn);
    }
}
