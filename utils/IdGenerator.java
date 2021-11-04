
package utils;

import java.util.Random;

public class IdGenerator
{
    public static String getGenerateId(final String prefix) {
        final long currentTime = System.currentTimeMillis();
        int random = (int)Math.pow(10.0, 8.0) - 1;
        try {
            random = getNdigitalRandom(8);
        }
        catch (Exception ex) {}
        return prefix + currentTime + random;
    }
    
    public static int getNdigitalRandom(final int n) throws Exception {
        if (n < 0 || n > 9) {
            throw new Exception("getNdigitalRandom method the parameter n must between 1 and 9.");
        }
        final int maxNum = (int)Math.pow(10.0, n);
        int num = new Random().nextInt(maxNum);
        if (num < Math.pow(10.0, n - 1)) {
            num = maxNum - num;
        }
        return num;
    }
    
    public static void main(final String[] args) throws Exception {
        System.out.println(getNdigitalRandom(1));
        System.out.println(getNdigitalRandom(1));
        System.out.println(getNdigitalRandom(1));
        System.out.println(getNdigitalRandom(1));
        System.out.println(getNdigitalRandom(1));
        System.out.println(getNdigitalRandom(1));
        System.out.println(getNdigitalRandom(1));
        System.out.println(getNdigitalRandom(1));
        System.out.println(getNdigitalRandom(1));
        System.out.println(getNdigitalRandom(1));
        System.out.println(getNdigitalRandom(1));
        System.out.println(getNdigitalRandom(1));
        System.out.println(getNdigitalRandom(1));
        System.out.println(getNdigitalRandom(1));
        System.out.println(getNdigitalRandom(1));
        System.out.println(getNdigitalRandom(1));
        System.out.println(getGenerateId("AP"));
        System.out.println(getGenerateId("AP"));
        System.out.println(getGenerateId("AP"));
        System.out.println(getGenerateId("AP"));
    }
}
