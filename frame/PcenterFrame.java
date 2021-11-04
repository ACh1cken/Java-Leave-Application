// 
// Decompiled by Procyon v0.5.36
// 

package frame;

import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import leaveApplication.Staff;
import loginHandler.UserLoginHandler;
import java.awt.Component;
import javax.swing.JLabel;

public class PcenterFrame extends BaseWorkFrame
{
    private static final long serialVersionUID = -1740443273926945787L;
    private JLabel idLabel;
    private JLabel unameLabel;
    private JLabel pwdLabel;
    private JLabel realnameLabel;
    private JLabel supervisorLabel;
    private JLabel idValue;
    private JLabel unameValue;
    private JLabel pwdValue;
    private JLabel realnameValue;
    private JLabel supervisorValue;
    
    @Override
    public void init() {
        (this.idLabel = new JLabel("ID :")).setFont(this.textFont);
        this.idLabel.setSize(150, 30);
        this.idLabel.setLocation(100, 20);
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
        (this.idValue = new JLabel()).setFont(this.textFont);
        this.idValue.setSize(200, 30);
        this.idValue.setLocation(270, 20);
        (this.unameValue = new JLabel()).setFont(this.textFont);
        this.unameValue.setSize(200, 30);
        this.unameValue.setLocation(270, 60);
        (this.pwdValue = new JLabel()).setFont(this.textFont);
        this.pwdValue.setSize(200, 30);
        this.pwdValue.setLocation(270, 100);
        (this.realnameValue = new JLabel()).setFont(this.textFont);
        this.realnameValue.setSize(200, 30);
        this.realnameValue.setLocation(270, 140);
        (this.supervisorValue = new JLabel()).setFont(this.textFont);
        this.supervisorValue.setSize(200, 30);
        this.supervisorValue.setLocation(270, 180);
        this.panel.add(this.idLabel);
        this.panel.add(this.unameLabel);
        this.panel.add(this.pwdLabel);
        this.panel.add(this.realnameLabel);
        this.panel.add(this.supervisorLabel);
        this.panel.add(this.idValue);
        this.panel.add(this.unameValue);
        this.panel.add(this.pwdValue);
        this.panel.add(this.realnameValue);
        this.panel.add(this.supervisorValue);
        final Staff staff = UserLoginHandler.getLoginStaff();
        if (null != staff) {
            this.idValue.setText(staff.getId());
            this.unameValue.setText(staff.getUsername());
            this.realnameValue.setText(staff.getRealname());
            this.supervisorValue.setText(staff.getsupervisorId());
        }
    }
    
    @Override
    public void actionPerformed(final ActionEvent arg0) {
        CommanAction.actionPerformed(this, this.pcenterMenuItem, (JMenuItem)arg0.getSource());
    }
}
