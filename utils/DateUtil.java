// 
// Decompiled by Procyon v0.5.36
// 

package utils;

import java.util.Date;
import java.text.SimpleDateFormat;

public class DateUtil
{
    public static String getDateStr() {
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Date date = new Date();
        return df.format(date);
    }
}
