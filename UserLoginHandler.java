# Java-Leave-Application

public class UserLoginHandler {
	private static Staff LOGIN_STAFF ;

	public static boolean login(String username,String password){
		Staff staff = DataCenter.getStaffByUsername(username);
		if(null == staff){
			return false;
		}
		if(null == password ||  !password.equals(staff.getPassword())){
			return false;
		}
		if(staff.isDeleted()){
			return false;
		}
		LOGIN_STAFF = staff;
		return true;		
	}
	public static Staff getLoginStaff(){
		return LOGIN_STAFF;
	}
	public static boolean logout(){
		LOGIN_STAFF = null;
		return true;
	}
}

