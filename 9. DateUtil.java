# Java-Leave-Application

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String getDateStr(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return df.format(date);}}
