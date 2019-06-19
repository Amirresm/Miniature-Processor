package control.models.cpu;

public class Adder {
    public static String compute(String val1, String val2){
        long b1 = Long.parseLong(val1, 2);
        long b2 = Long.parseLong(val2, 2);
        long sum = b1 + b2;
        return Utility.decimalToString(sum, 32);
    }
}