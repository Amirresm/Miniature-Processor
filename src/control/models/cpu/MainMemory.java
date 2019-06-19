package control.models.cpu;

import java.util.HashMap;
import java.util.Map;

public class MainMemory {
    final static int mainMemBits = 32;
    int memSize = 50;

    public Map<String, String> memoryMap = new HashMap<>();
    private String address;
    private Signal memReadSignal;
    private Signal memWriteSignal;

    public MainMemory() {

        for (int i = 0; i < memSize; i++) {
            String key = String.format("%" + mainMemBits + "s", Integer.toBinaryString(i)).replace(' ', '0');
            memoryMap.put(key, "00000000000000000000000000000000");
        }
    }

    public void setup(String address, Signal memReadSignal, Signal memWriteSignal) {
        this.address = address;
        this.memReadSignal = memReadSignal;
        this.memWriteSignal = memWriteSignal;
    }

    public void writeData(String data) {
        if (memWriteSignal.data == 1 && data.length() == 32)
            memoryMap.replace(address, data);
    }

    public String readData() {
        if (memReadSignal.data == 1)
            return memoryMap.get(address);
        else return "";
    }

    public void predefineData(String data, int numericalAddress) {
        String key = Utility.decimalToString(numericalAddress, mainMemBits);
        memoryMap.replace(key, data);
    }
    public void predefineData(long data, int numericalAddress) {
        String key = Utility.decimalToString(numericalAddress, mainMemBits);
        String value = Utility.decimalToString(data, 32);
        memoryMap.replace(key, value);
    }
}