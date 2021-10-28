
import javax.swing.JMenuItem;

public class CommanAction {
	public static void actionPerformed(BaseWorkFrame frame, JMenuItem localMenu, JMenuItem clickMenu) {
		if(localMenu == clickMenu){
			return;
		}			
		boolean changed = false;
		if (clickMenu == frame.logoutMenuItem) {
			if (UserLoginHandler.logout()) {
				new LoginFrame();
				changed = true;
			}
		} else if (clickMenu == frame.pcenterMenuItem) {
			new PcenterFrame();
			changed = true;
		} else if (clickMenu == frame.myApplicationMenuItem) {
			new MyApplicationFrame();
			changed = true;
		} else if (clickMenu == frame.approvalProcessMenuItem) {
			new ApprovalProcessFrame();
			changed = true;
		}else if (clickMenu == frame.newStaffMenuItem) {
			new UserLoginHandler();
			changed = true;
		}else if (clickMenu == frame.newApplicationMenuItem) {
			new AddApplicationFrame();
			changed = true;
		}else if (clickMenu == frame.staffListMenuItem) {
			new StaffListFrame();
			changed = true;
		}
		
		if(changed){
			frame.setVisible(false);
		}}}

