package smtcl.mocs.utils.authority;

/**
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-9-29
 * Time: ÉÏÎç11:54
 */
public class ByteUtil {
    public static final char[] letters = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final char[] LETTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String bytes2Hex (byte[] bytes, char mod) {
        char[] pattern = mod == 'x' ? letters : LETTERS;
        StringBuilder builder = new StringBuilder ();
        for (byte b : bytes) {
            int index = (b >> 4) & 0xf;
            builder.append (pattern [index]);
            index = b & 0xf;
            builder.append (pattern [index]);
        }
        return builder.toString ();
    }

    public static byte[] long2Bytes (long value) {
        int[] index = {56, 48, 40, 32, 24, 16, 8, 0};
        byte[] ret = new byte[8];
        for (int i = 0; i < 8; i ++) {
            ret [i] = (byte) ((value >> index [i]) & 0xff);
        }
        return ret;
    }

    public static byte[] double2Bytes (double value) {
        int[] index = {56, 48, 40, 32, 24, 16, 8, 0};
        long tmp = Double.doubleToRawLongBits (value);
        byte[] ret = new byte[8];
        for (int i = 0; i < 8; i ++) {
            ret [i] = (byte) ((tmp >> index [i]) & 0xff);
        }
        return ret;
    }
}