


import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

public class PcenterFrame extends BaseWorkFrame{	
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
		idLabel = new JLabel("ID :");
		idLabel.setFont(textFont);
		idLabel.setSize(150, 30);
		idLabel.setLocation(100, 20);
		
		unameLabel = new JLabel("UserName:");
		unameLabel.setFont(textFont);
		unameLabel.setSize(150, 30);
		unameLabel.setLocation(100, 60);
		
		pwdLabel = new JLabel("Password:");
		pwdLabel.setFont(textFont);
		pwdLabel.setSize(150, 30);
		pwdLabel.setLocation(100, 100);
		
		realnameLabel = new JLabel("RealName:");
		realnameLabel.setFont(textFont);
		realnameLabel.setSize(150, 30);
		realnameLabel.setLocation(100, 140);
		
		supervisorLabel = new JLabel("Supervisor:");
		supervisorLabel.setFont(textFont);
		supervisorLabel.setSize(150, 30);
		supervisorLabel.setLocation(100, 180);
		
		idValue = new JLabel();
		idValue.setFont(textFont);
		idValue.setSize(200, 30);
		idValue.setLocation(270, 20);
		
		unameValue = new JLabel();
		unameValue.setFont(textFont);
		unameValue.setSize(200, 30);
		unameValue.setLocation(270, 60);
		
		pwdValue = new JLabel();
		pwdValue.setFont(textFont);
		pwdValue.setSize(200, 30);
		pwdValue.setLocation(270, 100);
		
		realnameValue = new JLabel();
		realnameValue.setFont(textFont);
		realnameValue.setSize(200, 30);
		realnameValue.setLocation(270, 140);
		
		supervisorValue = new JLabel();
		supervisorValue.setFont(textFont);
		supervisorValue.setSize(200, 30);
		supervisorValue.setLocation(270, 180);
		
		this.panel.add(idLabel);
		this.panel.add(unameLabel);
		this.panel.add(pwdLabel);
		this.panel.add(realnameLabel);
		this.panel.add(supervisorLabel);
		this.panel.add(idValue);
		this.panel.add(unameValue);
		this.panel.add(pwdValue);
		this.panel.add(realnameValue);
		this.panel.add(supervisorValue);
		
		Staff staff = UserLoginHandler.getLoginStaff();
		if(null != staff){
			idValue.setText(staff.getId());
			unameValue.setText(staff.getUsername());
			realnameValue.setText(staff.getRealname());
			supervisorValue.setText(staff.getsupervisorId());
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		CommanAction.actionPerformed(this, this.pcenterMenuItem, (JMenuItem)arg0.getSource());
	}
}

