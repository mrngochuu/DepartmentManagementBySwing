/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Do Ngoc Huu
 */
public class DepartmentManager extends JFrame {

    //GUI variables
    private JScrollPane scrollBar;
    private JTree tree;
    private JButton btnSavetoFile;
    private JButton btnDepNew;
    private JButton btnDepSave;
    private JButton btnDepRemove;
    private JButton btnEmpRemove;
    private JButton btnEmpNew;
    private JButton btnEmpSave;
    private JTextField txtDepCode;
    private JTextField txtDepName;
    private JTextField txtEmpName;
    private JTextField txtEmpCode;
    private JTextField txtEmpSalary;
    private JLabel lblTimeSave;
    private JLabel lblDepCode;
    private JLabel lblDepName;
    private JLabel lblEmpName;
    private JLabel lblEmpCode;
    private JLabel lblEmpSalary;
    private JPanel pnlEmp;
    private JPanel pnlDep;
    private JPanel pnlLeft;
    private JPanel pnlRight;

    //Function variables
    String filename = "employee.txt";
    DefaultMutableTreeNode root = null;
    DefaultMutableTreeNode curDepNode = null;
    DefaultMutableTreeNode curEmpNode = null;
    boolean addNewDep = true;
    boolean addNewEmp = true;

    public DepartmentManager() {
        initComponents();
        setSize(687, 450);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        root = (DefaultMutableTreeNode) (this.tree.getModel().getRoot());
        loadData(); //Loading initial data from file
        TreePath path = new TreePath(root);
        tree.expandPath(path);

        Thread autoSave = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                        btnSaveFileActionPerformed(null);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            }
        });

        autoSave.start();
    }

    private void initComponents() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        //Department Details
        //lable and textField Department
        lblDepCode = new JLabel("Code:");
        lblDepCode.setPreferredSize(new Dimension(50, 20));
        lblDepCode.setFont(new Font("Tahoma", 0, 13));
        txtDepCode = new JTextField(17);
        lblDepName = new JLabel("Name:");
        lblDepName.setPreferredSize(new Dimension(50, 20));
        lblDepName.setFont(new Font("Tahoma", 0, 13));
        txtDepName = new JTextField(17);
        //button Department
        btnDepNew = new JButton("New");
        btnDepNew.setFont(new Font("Tahoma", 0, 13));
        btnDepNew.setPreferredSize(new Dimension(65, 25));
        btnDepNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepNewActionPerformed(evt);
            }
        });

        btnDepRemove = new JButton("Remove");
        btnDepRemove.setFont(new Font("Tahoma", 0, 13));
        btnDepRemove.setPreferredSize(new Dimension(85, 25));
        btnDepRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepRemoveActionPerformed(evt);
            }
        });

        btnDepSave = new JButton("Save");
        btnDepSave.setFont(new Font("Tahoma", 0, 13));
        btnDepSave.setPreferredSize(new Dimension(65, 25));
        btnDepSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepSaveActionPerformed(evt);
            }
        });

        //panel Department
        pnlDep = new JPanel();
        pnlDep.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 16));
        pnlDep.setPreferredSize(new Dimension(273, 143));
        pnlDep.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Department Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14)));
        pnlDep.add(lblDepCode);
        pnlDep.add(txtDepCode);
        pnlDep.add(lblDepName);
        pnlDep.add(txtDepName);
        pnlDep.add(btnDepNew);
        pnlDep.add(btnDepRemove);
        pnlDep.add(btnDepSave);

        //Employee Details
        //Label Emp
        lblEmpCode = new JLabel("Code:");
        lblEmpName = new JLabel("Name:");
        lblEmpSalary = new JLabel("Salary:");
        lblEmpCode.setPreferredSize(new Dimension(50, 20));
        lblEmpName.setPreferredSize(new Dimension(50, 20));
        lblEmpSalary.setPreferredSize(new Dimension(50, 20));
        lblEmpCode.setFont(new Font("Tahoma", 0, 13));
        lblEmpName.setFont(new Font("Tahoma", 0, 13));
        lblEmpSalary.setFont(new Font("Tahoma", 0, 13));
        //TextField Emp
        txtEmpCode = new JTextField(17);
        txtEmpName = new JTextField(17);
        txtEmpSalary = new JTextField(17);
        //Button Emp
        btnEmpNew = new JButton("New");
        btnEmpSave = new JButton("Save");
        btnEmpRemove = new JButton("Remove");
        btnEmpNew.setFont(new Font("Tahoma", 0, 13));
        btnEmpSave.setFont(new Font("Tahoma", 0, 13));
        btnEmpRemove.setFont(new Font("Tahoma", 0, 13));
        btnEmpNew.setPreferredSize(new Dimension(65, 25));
        btnEmpRemove.setPreferredSize(new Dimension(85, 25));
        btnEmpSave.setPreferredSize(new Dimension(65, 25));
        btnEmpNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpNewActionPerformed(evt);
            }
        });
        btnEmpRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpRemoveActionPerformed(evt);
            }
        });
        btnEmpSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpSaveActionPerformed(evt);
            }
        });
        //Panel Emp
        pnlEmp = new JPanel();
        pnlEmp.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 16));
        pnlEmp.setPreferredSize(new Dimension(273, 183));
        pnlEmp.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Employee Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14)));
        pnlEmp.add(lblEmpCode);
        pnlEmp.add(txtEmpCode);
        pnlEmp.add(lblEmpName);
        pnlEmp.add(txtEmpName);
        pnlEmp.add(lblEmpSalary);
        pnlEmp.add(txtEmpSalary);
        pnlEmp.add(btnEmpNew);
        pnlEmp.add(btnEmpRemove);
        pnlEmp.add(btnEmpSave);

        //lblTimeSave
        lblTimeSave = new JLabel();
        lblTimeSave.setPreferredSize(new Dimension(273, 20));
        lblTimeSave.setFont(new Font("Tahoma", 0, 13));

        //Panel Right
        pnlRight = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pnlRight.setPreferredSize(new Dimension(273, 400));
        pnlRight.add(lblTimeSave);
        pnlRight.add(pnlDep);
        pnlRight.add(pnlEmp);

        //Tree and SavetoFile button
        scrollBar = new JScrollPane();
        scrollBar.setViewportView(tree);
        tree = new JTree();
        tree.setBorder(new LineBorder(Color.BLACK, 1));
        tree.setPreferredSize(new Dimension(273, 330));
        DefaultMutableTreeNode treeNodel = new DefaultMutableTreeNode("Department");
        tree.setModel(new javax.swing.tree.DefaultTreeModel(treeNodel));
        tree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                treeMouseClicked(evt);
            }
        });
        btnSavetoFile = new JButton("Save to File");
        btnSavetoFile.setPreferredSize(new Dimension(273, 25));
        btnSavetoFile.setFont(new Font("Tahoma", 0, 15));
        btnSavetoFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveFileActionPerformed(evt);
            }
        });

        //Panel Left
        pnlLeft = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        pnlLeft.setPreferredSize(new Dimension(273, 400));
        pnlLeft.add(tree);
        pnlLeft.add(scrollBar);
        pnlLeft.add(btnSavetoFile);

        //Container
        Container container = getRootPane();
        container.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 16));
        container.setSize(new Dimension(576, 450));
        container.add(pnlLeft);
        container.add(pnlRight);
    }

    private void btnDepNewActionPerformed(java.awt.event.ActionEvent evt) {
        //Make the GUI ready for a new department details entered
        this.addNewDep = true;
        this.txtDepCode.setText("");
        this.txtDepName.setText("");
        this.txtEmpCode.setText("");
        this.txtEmpName.setText("");
        this.txtEmpSalary.setText("");
        this.txtDepCode.setEditable(true);
        this.txtDepCode.requestFocus();
    }

    private void btnDepSaveActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            //Save department details
            String code = this.txtDepCode.getText().trim().toUpperCase();
            txtDepCode.setText(code);
            String name = this.txtDepName.getText().trim();
            txtDepName.setText(name);
            if (validDepDetails() == false) {
                return;
            }
            if (addNewDep == true) {
                Department newDep = new Department(code, name);
                root.add(new DefaultMutableTreeNode(newDep));
            } else {
                ((Department) curDepNode.getUserObject()).setDepName(name);
            }
            this.tree.updateUI();
            this.addNewDep = false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please click the New button to add new Department.");
        }
    }

    private void btnDepRemoveActionPerformed(java.awt.event.ActionEvent evt) {
        //Removing a department
        if (this.curDepNode.getChildCount() > 0) {
            String msg = "Remove all emloyees before deleting a department.";
            JOptionPane.showMessageDialog(this, msg);
        } else {
            int response = JOptionPane.showConfirmDialog(this, "Delete this department?");
            if (response == JOptionPane.OK_OPTION) {
                root.remove(this.curDepNode);
                tree.updateUI();
            }
        }
    }

    private void btnEmpRemoveActionPerformed(java.awt.event.ActionEvent evt) {
        if (this.curEmpNode != null) {
            int response = JOptionPane.showConfirmDialog(this, "Delete this employee?");
            if (response == JOptionPane.OK_OPTION) {
                curDepNode.remove(this.curEmpNode);
                tree.updateUI();
            }
        }
    }

    private void btnEmpSaveActionPerformed(java.awt.event.ActionEvent evt) {
        //Save employee details
        try {
            String code = this.txtEmpCode.getText().trim().toUpperCase();
            txtEmpCode.setText(code);
            String name = this.txtEmpName.getText().trim();
            txtEmpName.setText(name);
            String salalyStr = this.txtEmpSalary.getText().trim();
            txtEmpSalary.setText(salalyStr);
            int sal = Integer.parseInt(salalyStr);
            if (validEmpDetails() == false) {
                return;
            }
            if (addNewEmp == true) {
                Employee newEmp = new Employee(code, name, sal);
                curDepNode.add(new DefaultMutableTreeNode(newEmp));
            } else {
                Employee emp = (Employee) (curEmpNode.getUserObject());
                emp.setEmpName(name);
                emp.setSalary(sal);
            }
            this.tree.updateUI();
            this.addNewEmp = false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please click the New button to add new Employee.");
        }

    }

    private void btnEmpNewActionPerformed(java.awt.event.ActionEvent evt) {
        this.addNewEmp = true;
        this.txtEmpCode.setText("");
        this.txtEmpName.setText("");
        this.txtEmpSalary.setText("");
        this.txtEmpCode.setEditable(true);
        this.txtEmpCode.requestFocus();
    }

    public boolean validDepDetails() {
        String s = "";
        if (addNewDep == true) { //checking the code
            s = this.txtDepCode.getText().trim().toUpperCase();
            this.txtDepCode.setText(s);
            if (!s.matches("^[A-Z]{2}$")) { // checking code format
                JOptionPane.showMessageDialog(this, "Code format is XX. (X stands for uppercase letters)");
                return false;
            }
            Enumeration depts = root.children(); // get departments
            while (depts.hasMoreElements()) {
                DefaultMutableTreeNode depNode = (DefaultMutableTreeNode) depts.nextElement();
                Department dept = (Department) (depNode.getUserObject());
                if (s.equalsIgnoreCase(dept.getDepCode())) {
                    JOptionPane.showMessageDialog(this, "Code is already exist.");
                    return false;
                }
            }
        }
        //checking the name
        s = this.txtDepName.getText().trim();
        this.txtEmpName.setText(s);
        if (s.length() == 0) {
            JOptionPane.showMessageDialog(this, "Name is required.");
            return false;
        }
        return true;
    }

    public boolean validEmpDetails() {
        String s = "";
        if (addNewEmp == true) { //checking the code
            s = this.txtEmpCode.getText().trim().toUpperCase();
            this.txtEmpCode.setText(s);
            if (!s.matches("^E\\d{3}$")) { // checking code format
                JOptionPane.showMessageDialog(this, "Code format is E001.");
                return false;
            }
            Enumeration depts = root.children(); // get departments
            while (depts.hasMoreElements()) {
                DefaultMutableTreeNode depNode = (DefaultMutableTreeNode) depts.nextElement();
                Enumeration emps = depNode.children();
                while (emps.hasMoreElements()) {
                    DefaultMutableTreeNode empNode = (DefaultMutableTreeNode) emps.nextElement();
                    Employee emp = (Employee) (empNode.getUserObject());
                    if (s.equalsIgnoreCase(emp.getEmpCode())) {
                        JOptionPane.showMessageDialog(this, "Code is already exist.");
                        return false;
                    }
                }
            }
        }
        //checking the name
        s = this.txtEmpName.getText().trim();
        this.txtEmpName.setText(s);
        if (s.length() == 0) {
            JOptionPane.showMessageDialog(this, "Name is required.");
            return false;
        }
        //checking the salary
        s = this.txtEmpSalary.getText().trim();
        if (!s.matches("^\\d+$")) {
            JOptionPane.showMessageDialog(this, "Salary is an integer.");
            return false;
        }
        return true;
    }

    private void treeMouseClicked(java.awt.event.MouseEvent evt) {
        //turn of the on-tree editting mode
        tree.cancelEditing();
        //Get the clicked node at the last component of the path
        TreePath path = tree.getSelectionPath();
        if (path == null) {
            return;
        }
        DefaultMutableTreeNode selectedNode = null;
        selectedNode = (DefaultMutableTreeNode) (path.getLastPathComponent());
        //Get the selected object in the model
        Object selectedObj = selectedNode.getUserObject();
        //Checking what is the selected object
        if (selectedNode == root) {
            this.curDepNode = this.curEmpNode = null;
        } else {
            if (selectedObj instanceof Department) {
                this.curDepNode = selectedNode;
                this.curEmpNode = null;
            } else if (selectedObj instanceof Employee) {
                curEmpNode = selectedNode;
                curDepNode = (DefaultMutableTreeNode) (selectedNode.getParent());
            }
        }
        viewDeptAndEmp();
    }

    private void btnSaveFileActionPerformed(java.awt.event.ActionEvent evt) {
        //Saving details to the file
        if (root.getChildCount() == 0) {
            return;
        }
        String S;
        try {
            FileWriter f = new FileWriter(filename);
            PrintWriter pf = new PrintWriter(f);
            Enumeration depts = root.children(); // get departments
            while (depts.hasMoreElements()) {
                DefaultMutableTreeNode depNode = (DefaultMutableTreeNode) depts.nextElement();
                Department dept = (Department) (depNode.getUserObject());
                S = dept.getDepCode() + "-" + dept.getDepName() + ":";
                pf.println(S);
                Enumeration emps = depNode.children(); // get employee
                while (emps.hasMoreElements()) {
                    DefaultMutableTreeNode empNode = (DefaultMutableTreeNode) emps.nextElement();
                    Employee emp = (Employee) (empNode.getUserObject());
                    S = emp.getEmpCode() + "," + emp.getEmpName() + "," + emp.getSalary();
                    pf.println(S);
                }
            }
            pf.close();
            f.close();
            String time = new Date(System.currentTimeMillis()).toString();
            lblTimeSave.setText("Last saved: " + time);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    public void loadData() {
        String S = "";
        StringTokenizer stk;
        try {
            FileReader f = new FileReader(filename);
            BufferedReader bf = new BufferedReader(f);
            while ((S = bf.readLine()) != null) {
                S = S.trim();
                boolean isDept = (S.charAt(S.length() - 1) == ':');
                stk = new StringTokenizer(S, "-:,");
                String code = stk.nextToken().trim();
                String name = stk.nextToken().trim();
                if (isDept) { // department details
                    curDepNode = new DefaultMutableTreeNode(new Department(code, name));
                    root.add(curDepNode);
                } else { // emloyee details
                    int salary = Integer.parseInt(stk.nextToken().trim());
                    curEmpNode = new DefaultMutableTreeNode(new Employee(code, name, salary));
                    curDepNode.add(curEmpNode);
                }
            }
            bf.close();
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewDeptAndEmp() {
        Department curDep = null;
        Employee curEmp = null;
        if (curDepNode != null) {
            curDep = (Department) (curDepNode.getUserObject());
        }

        if (curEmpNode != null) {
            curEmp = (Employee) (curEmpNode.getUserObject());
        }

        this.txtDepCode.setText(curDep != null ? curDep.getDepCode() : "");
        this.txtDepName.setText(curDep != null ? curDep.getDepName() : "");
        this.txtEmpCode.setText(curEmp != null ? curEmp.getEmpCode() : "");
        this.txtEmpName.setText(curEmp != null ? curEmp.getEmpName() : "");
        this.txtEmpSalary.setText("" + (curEmp != null ? curEmp.getSalary() : ""));
        this.txtDepCode.setEditable(false);
        this.txtEmpCode.setEditable(false);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DepartmentManager();
            }
        });
    }
}
