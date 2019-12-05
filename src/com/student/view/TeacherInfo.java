package com.student.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import com.student.AppConstants;
import com.student.base.BaseDAO.TeacherExistException;
import com.student.base.BaseDAO.TeacherNotFoundException;
import com.student.base.BaseDAO.TeacherTeachCourseException;
import com.student.dao.AdminDAO;

public class TeacherInfo extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel container;
    private JTable teaMess;
    private static final String[] infocolumn =
            {AppConstants.TNO, AppConstants.TNAME, AppConstants.PASSWORD, AppConstants.CDEPT, AppConstants.TTEL};
    private JLabel totCount;

    public TeacherInfo(AdminView frame) {
        super(frame, AppConstants.ADMIN_TEACHERINFO, true);
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(450, 300);
        setTitle(AppConstants.ADMIN_TEACHERINFO);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        container = new JPanel();
        setContentPane(container);
        container.setLayout(new BorderLayout(5, 5));

        initBtn();
        initTable();

    }

    public void initBtn() {
        JPanel panel = new JPanel();
        container.add(panel, BorderLayout.EAST);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton addBtn = new JButton(AppConstants.ADMIN_TEACHERINFO_ADD);
        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton delBtn = new JButton(AppConstants.ADMIN_TEACHERINFO_DEL);
        delBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton quitBtn = new JButton(AppConstants.EXIT);
        quitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(100, 30)));
        panel.add(addBtn);
        panel.add(Box.createRigidArea(new Dimension(100, 30)));
        panel.add(delBtn);
        panel.add(Box.createRigidArea(new Dimension(100, 30)));
        panel.add(quitBtn);

        addBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AddTeacher ac = new AddTeacher(TeacherInfo.this);
                ac.setVisible(true);
            }
        });
        delBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DelTeacher dc = new DelTeacher(TeacherInfo.this);
                dc.setVisible(true);
            }
        });
        quitBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void initTable() {
        JPanel panel = new JPanel();
        container.add(panel, BorderLayout.CENTER);
        teaMess = new JTable();
        teaMess.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        totCount = new JLabel();
        panel.add(totCount, BorderLayout.NORTH);
        teaMess.setEnabled(false);
        teaMess.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(teaMess);
        scrollPane.setPreferredSize(new Dimension(300, 180));
        panel.add(scrollPane);
        genTable();
    }

    public void genTable() {
        String[][] result = AdminDAO.getInstance().getAllTeachers();
        teaMess.setModel(new DefaultTableModel(result, infocolumn) {
            private static final long serialVersionUID = 1L;
        });
        totCount.setText(AppConstants.TOTAL_COUNT + String.valueOf(teaMess.getRowCount()));
    }

    private class AddTeacher extends JDialog {

        private static final long serialVersionUID = 1L;
        private JPanel contPanel;
        private JTextField[] tFields;
        private final String[] checkregex = {AppConstants.REGEX_TNO, AppConstants.REGEX_TNAME, AppConstants.REGEX_PASSWORD,
        										AppConstants.REGEX_TDEPT, AppConstants.REGEX_TTEL,};
        private final boolean checknull[] = {false, false, false, false, false};

        public AddTeacher(TeacherInfo frame) {
            super(frame, AppConstants.ADMIN_TEACHERINFO_ADD, true);
            contPanel = new JPanel();
            setContentPane(contPanel);
            setLayout(new BorderLayout(5, 5));
            setResizable(false);
            setLocationRelativeTo(null);
            setSize(310, 330);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            initBtn();
            initTable();
        }

        public void initBtn() {
            JPanel panel = new JPanel();
            JButton jb = new JButton(AppConstants.VERIFY);
            panel.add(jb);
            contPanel.add(panel, BorderLayout.SOUTH);
            getRootPane().setDefaultButton(jb);
            jb.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String[] info = new String[5];
                    for (int i = 0; i < 5; i++) {
                        info[i] = tFields[i].getText();
                    }
                    boolean isValid = true;
                    for (int i = 0; i < 5; i++) {
                        if (Pattern.matches(checkregex[i], info[i]) == false) {
                            isValid = false;
                            tFields[i].setBackground(Color.PINK);
                        } else {
                            tFields[i].setBackground(Color.WHITE);
                        }
                        if (checknull[i] && info[i].equals("")) {
                            info[i] = null;
                        }
                    }
                    if (!isValid) {
                        return;
                    }
                    try {
                        AdminDAO.getInstance().AddTeacher(info);
                    } catch (TeacherExistException e1) {
                        JOptionPane.showMessageDialog(null, AppConstants.ADMIN_SNO_EXIST_ERROR,
                                AppConstants.ERROR, JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    dispose();
                    TeacherInfo.this.genTable();
                }
            });
        }

        public void initTable() {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
            JPanel[] panels = new JPanel[5];
            JLabel[] labels = new JLabel[5];
            tFields = new JTextField[5];
            for (int i = 0; i < 5; i++) {
                panels[i] = new JPanel();
                panels[i].setLayout(new GridLayout(1, 2, 5, 5));
                labels[i] = new JLabel(infocolumn[i]);
                tFields[i] = new JTextField(10);
                panels[i].add(labels[i], Component.CENTER_ALIGNMENT);
                panels[i].add(tFields[i], Component.CENTER_ALIGNMENT);
                panel.add(panels[i]);
            }
            contPanel.add(panel, BorderLayout.CENTER);
        }
    }

    private class DelTeacher extends JDialog {

        private static final long serialVersionUID = 1L;
        private JPanel contPanel;
        private JTextField tField;

        public DelTeacher(TeacherInfo frame) {
            super(frame, AppConstants.ADMIN_TEACHERINFO_DEL, true);
            contPanel = new JPanel();
            setContentPane(contPanel);
            setLayout(new BorderLayout(5, 5));
            setResizable(false);
            setLocationRelativeTo(null);
            setSize(280, 120);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            initBtn();
            initTable();
        }

        public void initBtn() {
            JPanel panel = new JPanel();
            JButton jb = new JButton(AppConstants.DELETE);
            panel.add(jb);
            contPanel.add(panel, BorderLayout.SOUTH);
            getRootPane().setDefaultButton(jb);
            jb.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String tno = tField.getText();
                    if (Pattern.matches(AppConstants.REGEX_TNO, tno) == false) {
                        tField.setBackground(Color.PINK);
                        return;
                    } else {
                        tField.setBackground(Color.WHITE);
                    }
                    try {
                        AdminDAO.getInstance().DelTeacher(tno);
                    } catch (TeacherNotFoundException e1) {
                        JOptionPane.showMessageDialog(null, AppConstants.ADMIN_TNO_NOTEXIST_ERROR,
                                AppConstants.ERROR, JOptionPane.ERROR_MESSAGE);
                        return;
                    } catch (TeacherTeachCourseException e2) {
                        JOptionPane.showMessageDialog(null, AppConstants.ADMIN_TEACHCOURSE_ERROR,
                                AppConstants.ERROR, JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    dispose();
                    TeacherInfo.this.genTable();
                }
            });
        }

        public void initTable() {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
            JPanel panel2 = new JPanel();
            panel2.setLayout(new GridLayout(1, 2, 5, 5));
            JLabel label = new JLabel(AppConstants.TNO);
            tField = new JTextField(10);
            panel2.add(label, Component.CENTER_ALIGNMENT);
            panel2.add(tField, Component.LEFT_ALIGNMENT);
            panel.add(panel2);
            contPanel.add(panel, BorderLayout.CENTER);
        }
    }
}
