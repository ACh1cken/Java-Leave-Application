// 
// Decompiled by Procyon v0.5.36
// 

package utils;

public class StringUtil
{
    public static boolean isBlank(final String str) {
        return null == str || "".equals(str.trim());
    }
    
    public static boolean isNotBlank(final String str) {
        return !isBlank(str);
    }
}
