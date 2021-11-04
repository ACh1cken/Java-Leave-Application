
package loginHandler;

import data.DataCenter;
import leaveApplication.Staff;

public class UserLoginHandler
{
    private static Staff LOGIN_STAFF;
    
    public static boolean login(final String username, final String password) {
        final Staff staff = DataCenter.getStaffByUsername(username);
        if (null == staff) {
            return false;
        }
        if (null == password || !password.equals(staff.getPassword())) {
            return false;
        }
        if (staff.isDeleted()) {
            return false;
        }
        UserLoginHandler.LOGIN_STAFF = staff;
        return true;
    }
    
    public static Staff getLoginStaff() {
        return UserLoginHandler.LOGIN_STAFF;
    }
    
    public static boolean logout() {
        UserLoginHandler.LOGIN_STAFF = null;
        return true;
    }
}
