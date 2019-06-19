package control.models.cpu;

public class AndUnit {
    public static Signal and(Signal val1, Signal val2) {
        if (val2.data == 1 && val1.data == 1)
            return new Signal(1);
        else
            return new Signal(0);
    }
}