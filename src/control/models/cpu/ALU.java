package control.models.cpu;

public class ALU {
    private Signal zeroSignal = new Signal(0);
    public String compute (String val1, String val2, Signal ALUOP) {
        zeroSignal.data = 0;
        String result = Utility.decimalToString(0, 32);
        switch (ALUOP.data)
        {
            case 1:
                result = add(val1,val2);
                break;
            case 2:
                result = sub(val1,val2);
                break;
            case 3:
                result = slt(val1,val2);
                break;
            case 4:
                result = or(val1,val2);
                break;
            case 5:
                result = and(val1,val2);
                break;
        }
        if(Long.parseLong(result,2) == 0)
            zeroSignal.data = 1;
        return result;
    }
    public static String add(String Reg1, String Reg2)
    {
        long b1 = Long.parseLong(Reg1, 2);
        long b2 = Long.parseLong(Reg2, 2);
        long sum = b1 + b2;
        return Utility.decimalToString(sum, 32);
    }
    public static String sub(String Reg1, String Reg2)
    {
        long b1 = Long.parseLong(Reg1, 2);
        long b2 = Long.parseLong(Reg2, 2);
        long sum = b1 - b2;
        return Utility.decimalToString(sum, 32);
    }
    public static String and(String Reg1, String Reg2)
    {
        long b1 = Long.parseLong(Reg1, 2);
        long b2 = Long.parseLong(Reg2, 2);
        long sum = b1 & b2;
        return Utility.decimalToString(sum, 32);
    }
    public static String or(String Reg1, String Reg2)
    {
        long b1 = Long.parseLong(Reg1, 2);
        long b2 = Long.parseLong(Reg2, 2);
        long sum = b1 | b2;
        return Utility.decimalToString(sum, 32);
    }
    public static String slt(String Reg1, String Reg2)
    {
        int result = 0;
        if(Long.parseLong(Reg1, 2) < Long.parseLong(Reg2, 2))
            result = 1;
        return Utility.decimalToString(result, 32);
    }
    public Signal getZeroSignal() {
        return zeroSignal;
    }
}