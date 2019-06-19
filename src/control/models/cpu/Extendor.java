package control.models.cpu;

public class Extendor {
    public static String singExtend(String offset) {
        StringBuilder sb;
        if (offset.charAt(0) == '0') {
            sb = new StringBuilder("0000000000000000");
        } else {
            sb = new StringBuilder("1111111111111111");
        }
        sb.append(offset);
        return sb.toString();
    }
}