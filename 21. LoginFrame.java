# Java-Leave-Application

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame implements ActionListener {
	private JLabel  titleLabel ;
	private JLabel  usernameLabel;
	private JLabel passwordLabel ;	
	private JTextField usernameText;
	private JPasswordField passwordText;
	private JButton Log InButton;
	private JButton resetButton;
	private JLabel msgLabel;
	private JCheckBox showPassword;
	public LoginFrame(){
		super();
		init();
		setTitle("Login Page");
	}
	
	private void init(){
		JPanel panel=(JPanel)this.getContentPane();
		Font titleFont = new Font("Arial",Font.BOLD,28);
		Font checkbox = new Font("Arial",Font.SERIF PLAIN,15);
		Font textFont = new Font("Arial",Font.SERIF PLAIN,22);
		
		this.titleLabel = new JLabel("Leave Application System");
		this.titleLabel.setFont(titleFont);
		this.titleLabel.setForeground(Color.GREY);
		this.titleLabel.setSize(300, 150);
		this.titleLabel.setLocation(300, 30);
		
		this.usernameLabel = new JLabel("UserName:");
		this.usernameLabel.setFont(textFont);
		this.usernameLabel.setSize(100, 50);
		this.usernameLabel.setLocation(150, 150);
		
		this.passwordLabel = new JLabel("Password:");
		this.passwordLabel.setFont(textFont);
		this.passwordLabel.setSize(100, 50);
		this.passwordLabel.setLocation(150, 220);
		
		this.usernameText = new JTextField();
		this.usernameText.setFont(textFont);
		this.usernameText.setSize(300, 50);
		this.usernameText.setLocation(300, 150);
		
		this.passwordText = new JPasswordField();
		this.passwordText.setFont(textFont);
		this.passwordText.setSize(300, 50);
		this.passwordText.setLocation(300, 220);
		
		this.Log InButton = new JButton("Login");
		this.Log InButton.setFont(textFont);
		this.Log InButton.setSize(100, 50);
		this.Log InButton.setLocation(200, 330);
		this.Log InButton.addActionListener(this);
		
		this.resetButton = new JButton("Reset");
		this.resetButton.setFont(textFont);
		this.resetButton.setSize(100, 50);
		this.resetButton.setLocation(400, 330);
		this.resetButton.addActionListener(this);
		
		this.showPassword = new JCheckBox("Show Password");
		this.showPassword.setFont(checkbox);
		this.showPassword.setSize(150,50);
		this.showPassword.setLocation(300,270);
		this.showPassword.addActionListener(this);
		
		this.msgLabel = new JLabel();
		this.msgLabel.setFont(textFont);
		this.msgLabel.setSize(400, 50);
		this.msgLabel.setLocation(150, 400);
		
		panel.setLayout(null);
		panel.add(this.titleLabel);
		panel.add(this.usernameLabel);
		panel.add(this.passwordLabel);
		panel.add(this.usernameText);
		panel.add(this.passwordText);
		panel.add(this.Log InButton);
		panel.add(this.resetButton);
		panel.add(this.msgLabel);
		panel.add(this.showPassword);
		
	    this.setSize(800,600);

		Toolkit kit = Toolkit.getDefaultToolkit();  
	    Dimension screenSize = kit.getScreenSize(); 
	    int screenWidth = screenSize.width/2;   
	    int screenHeight = screenSize.height/2;  
	    int height = this.getHeight();
	    int width = this.getWidth();
	    this.setLocation(screenWidth-width/2, screenHeight-height/2);
	    this.setVisible(true);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == this.Log InButton){
			login();
		}else if(arg0.getSource() == this.resetButton){
			reset();
		}
		else if (arg0.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordText.setEchoChar((char) 0);
            } else {
                passwordText.setEchoChar('*');
            }}}
	
	
	@SuppressWarnings("deprecation")
	private void login(){
		this.msgLabel.setText("");
		boolean success = UserLoginHandler.login(this.usernameText.getText().trim(), this.passwordText.getText());
		if(success){
			this.setVisible(false);
			new PcenterFrame();
		}else{
			this.msgLabel.setText("username or password is incorrect!!!");
			this.msgLabel.setForeground(Color.YELLOW);
		}}
	private void reset(){
		this.msgLabel.setText("");
		this.usernameText.setText("");
		this.passwordText.setText("");
	}
	public static void main(String[] args){
		new LoginFrame();}}
