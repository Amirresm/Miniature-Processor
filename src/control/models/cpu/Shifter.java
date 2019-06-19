package control.models.cpu;

public class Shifter {
    public static String shift (String value, int amount) {
        long b1 = Long.parseLong(value , 2);
        b1 = b1 << amount;
        return Utility.decimalToString(b1, 32);
    }
}