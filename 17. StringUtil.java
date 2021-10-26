# Java-Leave-Application

public class StringUtil {
	
	public static boolean isBlank(String str){
		if(null == str){
			return true;
		}
		
		if("".equals(str.trim())){
			return true;
		}
		
		return false;
	}
	
	
	public static boolean isNotBlank(String str){
		return !isBlank(str);
	}
}
