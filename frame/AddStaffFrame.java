
package frame;

import javax.swing.JOptionPane;
import leaveApplication.Role;
import leaveApplication.Staff;
import data.DataCenter;
import utils.StringUtil;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class AddStaffFrame extends BaseWorkFrame
{
    private static final long serialVersionUID = -1740443273926945787L;
    private JLabel unameLabel;
    private JLabel pwdLabel;
    private JLabel realnameLabel;
    private JLabel supervisorLabel;
    private JTextField unameValue;
    private JTextField pwdValue;
    private JTextField realnameValue;
    private JTextField supervisorValue;
    private JButton submitButton;
    private JButton clearButton;
    private JLabel msgLabel;
    
    @Override
    public void init() {
        (this.unameLabel = new JLabel("UserName:")).setFont(this.textFont);
        this.unameLabel.setSize(150, 30);
        this.unameLabel.setLocation(100, 60);
        (this.pwdLabel = new JLabel("Password:")).setFont(this.textFont);
        this.pwdLabel.setSize(150, 30);
        this.pwdLabel.setLocation(100, 100);
        (this.realnameLabel = new JLabel("RealName:")).setFont(this.textFont);
        this.realnameLabel.setSize(150, 30);
        this.realnameLabel.setLocation(100, 140);
        (this.supervisorLabel = new JLabel("Supervisor:")).setFont(this.textFont);
        this.supervisorLabel.setSize(150, 30);
        this.supervisorLabel.setLocation(100, 180);
        (this.unameValue = new JTextField()).setFont(this.textFont);
        this.unameValue.setSize(200, 30);
        this.unameValue.setLocation(270, 60);
        (this.pwdValue = new JTextField()).setFont(this.textFont);
        this.pwdValue.setSize(200, 30);
        this.pwdValue.setLocation(270, 100);
        (this.realnameValue = new JTextField()).setFont(this.textFont);
        this.realnameValue.setSize(200, 30);
        this.realnameValue.setLocation(270, 140);
        (this.supervisorValue = new JTextField()).setFont(this.textFont);
        this.supervisorValue.setSize(200, 30);
        this.supervisorValue.setLocation(270, 180);
        (this.submitButton = new JButton("Submit")).setFont(this.textFont);
        this.submitButton.setSize(200, 30);
        this.submitButton.setLocation(100, 220);
        (this.clearButton = new JButton("Clear")).setFont(this.textFont);
        this.clearButton.setSize(200, 30);
        this.clearButton.setLocation(270, 220);
        (this.msgLabel = new JLabel()).setFont(this.textFont);
        this.msgLabel.setSize(400, 30);
        this.msgLabel.setLocation(100, 270);
        this.msgLabel.setForeground(Color.RED);
        this.panel.add(this.unameLabel);
        this.panel.add(this.pwdLabel);
        this.panel.add(this.realnameLabel);
        this.panel.add(this.supervisorLabel);
        this.panel.add(this.unameValue);
        this.panel.add(this.pwdValue);
        this.panel.add(this.realnameValue);
        this.panel.add(this.supervisorValue);
        this.panel.add(this.submitButton);
        this.panel.add(this.clearButton);
        this.panel.add(this.msgLabel);
        this.submitButton.addActionListener(this);
        this.clearButton.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(final ActionEvent arg0) {
        if (arg0.getSource() == this.submitButton) {
            this.addStaff();
            return;
        }
        if (arg0.getSource() == this.clearButton) {
            this.clear();
            return;
        }
        CommanAction.actionPerformed(this, this.newStaffMenuItem, (JMenuItem)arg0.getSource());
    }
    
    private boolean addStaff() {
        this.msgLabel.setText("");
        if (StringUtil.isBlank(this.unameValue.getText())) {
            this.msgLabel.setText("User name can not be null!");
            return false;
        }
        if (StringUtil.isBlank(this.pwdValue.getText())) {
            this.msgLabel.setText("Password can not be null!");
            return false;
        }
        if (StringUtil.isBlank(this.realnameValue.getText())) {
            this.msgLabel.setText("Real name can not be null!");
            return false;
        }
        if (StringUtil.isBlank(this.supervisorValue.getText())) {
            this.msgLabel.setText("Supervisor can not be null!");
            return false;
        }
        final Staff supervisor = DataCenter.getStaffByUsername(this.supervisorValue.getText().trim());
        if (null == supervisor) {
            this.msgLabel.setText("Supervisor does not exist!");
            return false;
        }
        final Staff staff = new Staff();
        staff.setUsername(this.unameValue.getText().trim());
        staff.setRole(Role.STAFF);
        staff.setRealname(this.realnameValue.getText().trim());
        staff.setPassword(this.pwdValue.getText().trim());
        staff.setsupervisorId(supervisor.getId());
        final boolean success = DataCenter.addStaff(staff);
        if (success) {
            JOptionPane.showMessageDialog(null, "Added Staff Succesfully");
        }
        else {
            JOptionPane.showMessageDialog(null, "Failed to add staff");
        }
        return success;
    }
    
    private void clear() {
        this.unameValue.setText("");
        this.pwdValue.setText("");
        this.realnameValue.setText("");
        this.supervisorValue.setText("");
        this.msgLabel.setText("");
    }
}
