
package frame;

import java.awt.Dimension;
import leaveApplication.Staff;
import java.awt.Toolkit;
import leaveApplication.Role;
import java.awt.LayoutManager;
import loginHandler.UserLoginHandler;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public abstract class BaseWorkFrame extends JFrame implements ActionListener
{
    private static final long serialVersionUID = -8822851657224364435L;
    JMenuBar menuBar;
    JMenu pcenterMenu;
    JMenu applicationMenu;
    JMenuItem pcenterMenuItem;
    JMenuItem staffListMenuItem;
    JMenuItem myApplicationMenuItem;
    JMenuItem approvalProcessMenuItem;
    JMenuItem newApplicationMenuItem;
    JMenuItem newStaffMenuItem;
    JMenuItem logoutMenuItem;
    Font textFont;
    JPanel panel;
    
    public abstract void init();
    
    public BaseWorkFrame() {
        final Staff staff = UserLoginHandler.getLoginStaff();
        if (null == staff) {
            this.setVisible(false);
            new LoginFrame();
            return;
        }
        this.textFont = new Font("Arial", 0, 14);
        (this.panel = (JPanel)this.getContentPane()).setLayout(null);
        final String welcome = "Leave Application System";
        this.menuBar = new JMenuBar();
        (this.pcenterMenu = new JMenu("User Center")).setFont(this.textFont);
        (this.applicationMenu = new JMenu("Application")).setFont(this.textFont);
        this.pcenterMenuItem = new JMenuItem("Personal Center");
        this.staffListMenuItem = new JMenuItem("Staff List");
        this.newStaffMenuItem = new JMenuItem("New Staff");
        this.logoutMenuItem = new JMenuItem("Logout");
        this.myApplicationMenuItem = new JMenuItem("My Application");
        this.approvalProcessMenuItem = new JMenuItem("Approval Process");
        this.newApplicationMenuItem = new JMenuItem("New Application");
        this.menuBar.add(this.pcenterMenu);
        this.menuBar.add(this.applicationMenu);
        this.pcenterMenu.add(this.pcenterMenuItem);
        if (staff.getRole() == Role.DIRECTOR) {
            this.pcenterMenu.add(this.newStaffMenuItem);
            this.pcenterMenu.add(this.staffListMenuItem);
        }
        else {
            this.applicationMenu.add(this.myApplicationMenuItem);
            this.applicationMenu.add(this.newApplicationMenuItem);
        }
        this.applicationMenu.add(this.approvalProcessMenuItem);
        this.pcenterMenu.add(this.logoutMenuItem);
        this.pcenterMenuItem.addActionListener(this);
        this.newStaffMenuItem.addActionListener(this);
        this.logoutMenuItem.addActionListener(this);
        this.myApplicationMenuItem.addActionListener(this);
        this.approvalProcessMenuItem.addActionListener(this);
        this.newApplicationMenuItem.addActionListener(this);
        this.staffListMenuItem.addActionListener(this);
        this.setTitle(welcome);
        this.setJMenuBar(this.menuBar);
        this.setSize(800, 600);
        final Toolkit kit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = kit.getScreenSize();
        final int screenWidth = screenSize.width / 2;
        final int screenHeight = screenSize.height / 2;
        final int height = this.getHeight();
        final int width = this.getWidth();
        this.setLocation(screenWidth - width / 2, screenHeight - height / 2);
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
        this.init();
    }
}
