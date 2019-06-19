package control.models.cpu;

public class Utility {
    public static String decimalToString(int number, int leadingZeros) {
        return String.format("%" + leadingZeros + "s", Integer.toBinaryString(number)).replace(' ', '0');
    }
    public static String decimalToString(long number, int leadingZeros) {
        if(Long.toBinaryString(number).length() <= leadingZeros) {
            return String.format("%" + leadingZeros + "s", Long.toBinaryString(number)).replace(' ', '0');
        }
        else {
            String longBin = Long.toBinaryString(number);
            while (longBin.length() > leadingZeros) {
                longBin = longBin.substring(1);
            }
            return longBin;
        }
    }
}

