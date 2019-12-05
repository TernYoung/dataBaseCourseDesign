package com.student.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import com.student.AppConstants;
import com.student.dao.StudentDAO;
import com.student.dao.TeacherDAO;
import com.student.model.Student;
import com.student.model.Teacher;

/**
 * @Description: LoginView
 * @ClassName: LoginView
 * 
 */
public class LoginView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField userField;
    private JPasswordField passwordField;

    public LoginView() {
        setResizable(false);
        setTitle("用户登录");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(4, 1));

        contentPane.add(new JPanel());

        JPanel userPanel = new JPanel();
        contentPane.add(userPanel);

        JLabel userLabel = new JLabel("用户名：");
        userPanel.add(userLabel);

        userField = new JTextField(12);
        userPanel.add(userField);

        JPanel passwordPanel = new JPanel();
        contentPane.add(passwordPanel);

        JLabel passwordLael = new JLabel("密    码：");
        passwordPanel.add(passwordLael);

        passwordField = new JPasswordField();
        passwordField.setColumns(12);
        passwordPanel.add(passwordField);

        JPanel btnPanel = new JPanel();
        contentPane.add(btnPanel);

        JButton loginbtn = new JButton("登录");
        btnPanel.add(loginbtn);
        loginbtn.addActionListener(new LoginListener());
        getRootPane().setDefaultButton(loginbtn);

        JButton exitbtn = new JButton("退出");
        exitbtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnPanel.add(exitbtn);
        setVisible(true);
    }

    private class LoginListener implements ActionListener {

    	 @Override
         public void actionPerformed(ActionEvent e) {

             String username = userField.getText();
             String password = String.valueOf(passwordField.getPassword());

             if (username.equals("admin")) {
                 if (password.equals("admin")) {
                     dispose();
                     new AdminView();
                 } else {
                     userField.setBackground(Color.PINK);
                     passwordField.setBackground(Color.PINK);
                     JOptionPane.showMessageDialog(null, AppConstants.LOGIN_ERROR);
                 }
             }
             else if(username.charAt(0)=='t') {
            	 String tno = TeacherDAO.getInstance().queryForLogin(username, password);
                 if (tno != null) {
                     dispose();
                     new TeacherView(new Teacher(tno));
                 } else {
                     userField.setBackground(Color.PINK);
                     passwordField.setBackground(Color.PINK);
                     JOptionPane.showMessageDialog(null, AppConstants.LOGIN_ERROR);
                 }
             }
             else {
                 String sno = StudentDAO.getInstance().queryForLogin(username, password);
                 if (sno != null) {
                     dispose();
                     new StudentView(new Student(sno));
                 } else {
                     userField.setBackground(Color.PINK);
                     passwordField.setBackground(Color.PINK);
                     JOptionPane.showMessageDialog(null, AppConstants.LOGIN_ERROR);
                 }
             }
         }
     }
 }

