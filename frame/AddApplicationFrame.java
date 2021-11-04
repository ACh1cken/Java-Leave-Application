
package frame;

import javax.swing.JOptionPane;
import data.DataCenter;
import leaveApplication.ApplicationStatus;
import loginHandler.UserLoginHandler;
import utils.DateUtil;
import leaveApplication.LeaveApplication;
import utils.StringUtil;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class AddApplicationFrame extends BaseWorkFrame
{
    private static final long serialVersionUID = -1740443273926945787L;
    private JLabel reasonLabel;
    private JLabel fromDateLabel;
    private JLabel toDateLabel;
    private JTextField reasonValue;
    private JTextField fromDateValue;
    private JTextField toDateValue;
    private JButton submitButton;
    private JButton clearButton;
    private JLabel msgLabel;
    
    @Override
    public void init() {
        (this.reasonLabel = new JLabel("reason:")).setFont(this.textFont);
        this.reasonLabel.setSize(150, 30);
        this.reasonLabel.setLocation(100, 60);
        (this.fromDateLabel = new JLabel("fromDate:")).setFont(this.textFont);
        this.fromDateLabel.setSize(150, 30);
        this.fromDateLabel.setLocation(100, 100);
        (this.toDateLabel = new JLabel("toDate:")).setFont(this.textFont);
        this.toDateLabel.setSize(150, 30);
        this.toDateLabel.setLocation(100, 140);
        (this.reasonValue = new JTextField()).setFont(this.textFont);
        this.reasonValue.setSize(200, 30);
        this.reasonValue.setLocation(270, 60);
        (this.fromDateValue = new JTextField()).setFont(this.textFont);
        this.fromDateValue.setSize(200, 30);
        this.fromDateValue.setLocation(270, 100);
        (this.toDateValue = new JTextField()).setFont(this.textFont);
        this.toDateValue.setSize(200, 30);
        this.toDateValue.setLocation(270, 140);
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
        this.panel.add(this.reasonLabel);
        this.panel.add(this.fromDateLabel);
        this.panel.add(this.toDateLabel);
        this.panel.add(this.reasonValue);
        this.panel.add(this.fromDateValue);
        this.panel.add(this.toDateValue);
        this.panel.add(this.submitButton);
        this.panel.add(this.clearButton);
        this.panel.add(this.msgLabel);
        this.submitButton.addActionListener(this);
        this.clearButton.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(final ActionEvent arg0) {
        if (arg0.getSource() == this.submitButton) {
            this.addApplication();
            return;
        }
        if (arg0.getSource() == this.clearButton) {
            this.clear();
            return;
        }
        CommanAction.actionPerformed(this, this.newApplicationMenuItem, (JMenuItem)arg0.getSource());
    }
    
    private boolean addApplication() {
        this.msgLabel.setText("");
        if (StringUtil.isBlank(this.reasonValue.getText())) {
            this.msgLabel.setText("Reason can not be null!");
            return false;
        }
        if (StringUtil.isBlank(this.fromDateValue.getText())) {
            this.msgLabel.setText("FromDate can not be null!");
            return false;
        }
        if (StringUtil.isBlank(this.toDateValue.getText())) {
            this.msgLabel.setText("ToDate can not be null!");
            return false;
        }
        final LeaveApplication application = new LeaveApplication();
        application.setReason(this.reasonValue.getText().trim());
        application.setFromDate(this.fromDateValue.getText().trim());
        application.setToDate(this.toDateValue.getText().trim());
        application.setCreateTime(DateUtil.getDateStr());
        application.setProposerId(UserLoginHandler.getLoginStaff().getId());
        application.setStatus(ApplicationStatus.PROCESSING);
        final boolean success = DataCenter.addLeaveApplication(application);
        if (success) {
            JOptionPane.showMessageDialog(null, "Application submission successful!");
        }
        else {
            JOptionPane.showMessageDialog(null, "Application submission failed!");
        }
        return success;
    }
    
    private void clear() {
        this.reasonValue.setText("");
        this.fromDateValue.setText("");
        this.toDateValue.setText("");
        this.msgLabel.setText("");
    }
}
