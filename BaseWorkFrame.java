
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public abstract class BaseWorkFrame extends JFrame implements ActionListener{
	JMenuBar menuBar ;
	JMenu pcenterMenu ;		
	JMenu applicationMenu ;	
	JMenuItem pcenterMenuItem;
	JMenuItem staffListMenuItem;
	JMenuItem myApplicationMenuItem;
	JMenuItem approvalProcessMenuItem;
	JMenuItem newApplicationMenuItem;
	JMenuItem newStaffMenuItem;
	JMenuItem logoutMenuItem;
	Font textFont ;
	JPanel panel;
	public abstract void init();
	public BaseWorkFrame() {
		Staff staff = UserLoginHandler.getLoginStaff();
		if(null == staff){
			this.setVisible(false);
			new LoginFrame();
			return;
		}
		
		this.textFont = new Font("SERIF",Font.PLAIN,20);
		
		panel=(JPanel)this.getContentPane();	
		panel.setLayout(null);
		
		String welcome = "Leave Application System";
		
		menuBar = new JMenuBar();
		pcenterMenu = new JMenu("User Session");
		pcenterMenu.setFont(textFont);
		
		applicationMenu = new JMenu("Application");
		applicationMenu.setFont(textFont);
		
		
		pcenterMenuItem = new JMenuItem("Personal Info");
		staffListMenuItem = new JMenuItem("Staff List");
		newStaffMenuItem = new JMenuItem("New Staff");
		logoutMenuItem = new JMenuItem("Logout");
		myApplicationMenuItem  = new JMenuItem("My Application");
		approvalProcessMenuItem = new JMenuItem("Approval Process");
		newApplicationMenuItem = new JMenuItem("New Application");

		menuBar.add(pcenterMenu);
		menuBar.add(applicationMenu);
		
		pcenterMenu.add(pcenterMenuItem);
		if(staff.getRole() == Role.DIRECTOR){
			pcenterMenu.add(newStaffMenuItem);
			pcenterMenu.add(staffListMenuItem);
			applicationMenu.add(approvalProcessMenuItem);
		}else{
			applicationMenu.add(myApplicationMenuItem);
			applicationMenu.add(newApplicationMenuItem);
		}
		
		pcenterMenu.add(logoutMenuItem);		
		this.pcenterMenuItem.addActionListener(this);
		this.newStaffMenuItem.addActionListener(this);
		this.logoutMenuItem.addActionListener(this);
		this.myApplicationMenuItem.addActionListener(this);
		this.approvalProcessMenuItem.addActionListener(this);
		this.newApplicationMenuItem.addActionListener(this);	
		this.staffListMenuItem.addActionListener(this);

		
		this.setTitle(welcome);
		this.setJMenuBar(menuBar);
		
		this.setSize(800, 600);

		Toolkit kit = Toolkit.getDefaultToolkit(); 
		Dimension screenSize = kit.getScreenSize(); 
		int screenWidth = screenSize.width / 2; 
		int screenHeight = screenSize.height / 2; 
		int height = this.getHeight();
		int width = this.getWidth();
		this.setLocation(screenWidth - width / 2, screenHeight - height / 2);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		init();
	}
}
