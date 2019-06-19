package control.models.cpu;

public class Mux {
    public static String compute(String val1, String val2, Signal flag) {
        if (flag.data == 0) return val1;
        else return val2;
    }
}