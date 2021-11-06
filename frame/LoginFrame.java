
package frame;

import loginHandler.UserLoginHandler;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import data.DataCenter;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;    
import java.awt.event.WindowListener;   
import javax.swing.JFrame;

public class LoginFrame extends JFrame implements ActionListener
{
    private static final long serialVersionUID = -2248657415132249L;
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameText;
    private JPasswordField passwordText;
    private JButton submitButton;
    private JButton clearButton;
    private JLabel msgLabel;
    private JLabel iconlabel;
    public LoginFrame() {
        this.init();
    }
    
    private void init() {
        final JPanel panel = (JPanel)this.getContentPane();
        final Font titleFont = new Font("SansSerif", 1, 24);
        final Font textFont = new Font("Serif", 0, 20);
        final Font buttonsFont = new Font ("Arial",0,18);
        this.iconlabel = new JLabel(new ImageIcon(getClass().getResource("/assets/Bundle_of_Balloons.png")));
        this.iconlabel.setBounds(178, 18, 139, 65);
        (this.titleLabel = new JLabel("Leave Application System")).setFont(titleFont);
        this.titleLabel.setHorizontalAlignment(JLabel.CENTER);
        this.titleLabel.setForeground(Color.DARK_GRAY);
        this.titleLabel.setSize(600, 50);
        this.titleLabel.setLocation(115, 30);
        (this.usernameLabel = new JLabel("Username:")).setFont(textFont);
        this.usernameLabel.setSize(100, 50);
        this.usernameLabel.setLocation(150, 150);
        (this.passwordLabel = new JLabel("Password:")).setFont(textFont);
        this.passwordLabel.setSize(100, 50);
        this.passwordLabel.setLocation(150, 220);
        (this.usernameText = new JTextField()).setFont(textFont);
        this.usernameText.setSize(300, 50);
        this.usernameText.setLocation(300, 150);
        (this.passwordText = new JPasswordField()).setFont(textFont);
        this.passwordText.setSize(300, 50);
        this.passwordText.setLocation(300, 220);
        (this.submitButton = new JButton("Login")).setFont(buttonsFont);
        this.submitButton.setSize(100, 50);
        this.submitButton.setLocation(200, 300);
        this.submitButton.addActionListener(this);
        (this.clearButton = new JButton("Clear")).setFont(buttonsFont);
        this.clearButton.setSize(100, 50);
        this.clearButton.setLocation(400, 300);
        this.clearButton.addActionListener(this);
        (this.msgLabel = new JLabel()).setFont(textFont);
        this.msgLabel.setSize(400, 50);
        this.msgLabel.setLocation(150, 400);
        panel.setLayout(null);

        panel.add(this.iconlabel);
        panel.add(this.titleLabel);
        panel.add(this.usernameLabel);
        panel.add(this.passwordLabel);
        panel.add(this.usernameText);
        panel.add(this.passwordText);
        panel.add(this.submitButton);
        panel.add(this.clearButton);
        panel.add(this.msgLabel);
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
    }
    
    @Override
    public void actionPerformed(final ActionEvent arg0) {
        if (arg0.getSource() == this.submitButton) {
            this.login();
        }
        else if (arg0.getSource() == this.clearButton) {
            this.clear();
        }
    }
    
    private void login() {
        this.msgLabel.setText("");
        final boolean success = UserLoginHandler.login(this.usernameText.getText().trim(), this.passwordText.getText());
        if (success) {
            this.setVisible(false);
            new PcenterFrame();
        }
        else {
            this.msgLabel.setText("Username or password is incorrect!");
            this.msgLabel.setForeground(Color.RED);
        }
    }
    
    private void clear() {
        this.msgLabel.setText("");
        this.usernameText.setText("");
        this.passwordText.setText("");
    }
    
    public static void main(final String[] args) {
        new LoginFrame();
    }

}