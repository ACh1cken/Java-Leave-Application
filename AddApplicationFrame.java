
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddApplicationFrame extends BaseWorkFrame{	
	private JLabel reasonLabel;
	private JLabel fromDateLabel;
	private JLabel toDateLabel;
	private JTextField reasonValue;
	private JTextField fromDateValue;
	private JTextField toDateValue;
	private JButton LogInButton;
	private JButton resetButton;
	private JLabel msgLabel;
	
	@Override
	public void init() {
		reasonLabel = new JLabel("Detail:");
		reasonLabel.setFont(textFont);
		reasonLabel.setSize(150, 30);
		reasonLabel.setLocation(100, 60);
		fromDateLabel = new JLabel("StartDate:");
		fromDateLabel.setFont(textFont);
		fromDateLabel.setSize(150, 30);
		fromDateLabel.setLocation(100, 100);
		
toDateLabel = new JLabel("EndDate:");
		toDateLabel.setFont(textFont);
		toDateLabel.setSize(150, 30);
		toDateLabel.setLocation(100, 140);
		reasonValue = new JTextField();
		reasonValue.setFont(textFont);
		reasonValue.setSize(200, 30);
		reasonValue.setLocation(270, 60);
		
		fromDateValue = new JTextField();
		fromDateValue.setFont(textFont);
		fromDateValue.setSize(200, 30);
		fromDateValue.setLocation(270, 100);
		
		toDateValue = new JTextField();
		toDateValue.setFont(textFont);
		toDateValue.setSize(200, 30);
		toDateValue.setLocation(270, 140);
		
		LogInButton = new JButton("Log In");
		LogInButton.setFont(textFont);
		LogInButton.setSize(200, 30);
		LogInButton.setLocation(100, 220);
		
		resetButton = new JButton("Reset");
		resetButton.setFont(textFont);
		resetButton.setSize(200, 30);
		resetButton.setLocation(270, 220);
		
		msgLabel =  new JLabel();
		msgLabel.setFont(textFont);
		msgLabel.setSize(400, 30);
		msgLabel.setLocation(100, 270);
		msgLabel.setForeground(Color.YELLOW);
		
		this.panel.add(reasonLabel);
		this.panel.add(fromDateLabel);
		this.panel.add(toDateLabel);
		this.panel.add(reasonValue);
		this.panel.add(fromDateValue);
		this.panel.add(toDateValue);
		this.panel.add(LogInButton);
		this.panel.add(resetButton);
		this.panel.add(msgLabel);
		
		LogInButton.addActionListener(this);
		resetButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == this.LogInButton){
			addApplication();
			return;
		}else if(arg0.getSource() == this.resetButton){
			reset();
			return;
		}
		CommanAction.actionPerformed(this, this.newApplicationMenuItem, (JMenuItem)arg0.getSource());
	}
	private boolean addApplication(){
		msgLabel.setText("");
		if(StringUtil.isBlank(reasonValue.getText())){
			msgLabel.setText("Details can not be empty!");
			return false;
		}
		if(StringUtil.isBlank(fromDateValue.getText())){
			msgLabel.setText("StartDate can not be empty!");
			return false;
		}
		if(StringUtil.isBlank(toDateValue.getText())){
			msgLabel.setText("EndDate can not be empty!");
			return false;
		}
		LeaveApplication application = new LeaveApplication();
		application.setReason(reasonValue.getText().trim());
		application.setFromDate(fromDateValue.getText().trim());
		application.setToDate(toDateValue.getText().trim());
		application.setCreateTime(DateUtil.getDateStr());
		application.setProposerId(UserLoginHandler.getLoginStaff().getId());
		application.setStatus(ApplicationStatus.PROCESSING);
		
		boolean success = DataCenter.addLeaveApplication(application);
		if(success){
			JOptionPane.showMessageDialog(null,"You have been successfully logged in!!!");
		}else{
			JOptionPane.showMessageDialog(null,"Failed to log in into Leave Application");
		}
		return success;
	}
	
	private void reset(){
		reasonValue.setText("");
		fromDateValue.setText("");
		toDateValue.setText("");
		msgLabel.setText("");
	}
	
}
