package control.models.cpu;

import control.models.MemTableCell;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class MainMemory {
    final static int mainMemBits = 32;

    public Map<String, String> memoryMap = new HashMap<>();
    private String address;
    private Signal memReadSignal;
    private Signal memWriteSignal;

    private ObservableList<MemTableCell> uiMemList;
    private int memSize;

    public MainMemory(ObservableList<MemTableCell> uiMemList) {
        this.uiMemList = uiMemList;
        resizeMemory(64);
    }

    public void resizeMemory(int memorySize) {
        this.memSize = memorySize;
        if (memorySize < 0)
            memorySize = 0;

        int radix = (int) Math.sqrt(memorySize - 1) + 1;
        uiMemList.clear();
        for (int i = 0; i < memorySize; i++) {
            String internalKey = Utility.decimalToString(i, mainMemBits);
            String uiKey = Utility.decimalToString(i, radix);
            String value = "00000000000000000000000000000000";
            memoryMap.put(internalKey, value);
            MemTableCell cell = new MemTableCell(i, uiKey, value);
            cell.setDecData(0);
            uiMemList.add(cell);
        }
    }

    public void setup(String address, Signal memReadSignal, Signal memWriteSignal) {
        this.address = address;
        this.memReadSignal = memReadSignal;
        this.memWriteSignal = memWriteSignal;
    }

    public void writeData(String data) {
        if (memWriteSignal.data == 1 && data.length() == 32) {
//            memoryMap.replace(address, data);
//            int tableIndex = Integer.parseInt(address, 2);
//            MemTableCell cell = uiMemList.get(tableIndex);
//            cell.setData(data);
//            uiMemList.set(tableIndex, cell);
            putInMemory(address, data);
        }
    }

    public String readData() {
        if (memReadSignal.data == 1)
            return memoryMap.get(address);
        else return "";
    }

    public void predefineData(String data, int numericalAddress) {
        String key = Utility.decimalToString(numericalAddress, mainMemBits);
        memoryMap.replace(key, data);
        uiMemList.get(numericalAddress).setData(data);
    }

    public void predefineData(long data, int numericalAddress) {
//        String key = Utility.decimalToString(numericalAddress, mainMemBits);
//        String value = Utility.decimalToString(data, 32);
//        memoryMap.replace(key, value);
//        MemTableCell cell = uiMemList.get(numericalAddress);
//        cell.setData(value);
//        uiMemList.set(numericalAddress, cell);
        putInMemory(numericalAddress, data);

    }

    public int getMemSize() {
        return memSize;
    }

    private void putInMemory(String addr, String registerWriteData) {
        if (memoryMap.containsKey(addr))
            memoryMap.replace(addr, registerWriteData);
        else
            memoryMap.put(addr, registerWriteData);
        int tableIndex = Integer.parseInt(addr, 2);
        MemTableCell cell = uiMemList.get(tableIndex);
        cell.setData(registerWriteData);
        cell.setDecData(Integer.parseInt(registerWriteData, 2));
        uiMemList.set(tableIndex, cell);
    }

    private void putInMemory(int addr, long registerWriteData) {
        String key = Utility.decimalToString(addr, mainMemBits);
        String value = Utility.decimalToString(registerWriteData, 32);
        if (memoryMap.containsKey(key))
            memoryMap.replace(key, value);
        else
            memoryMap.put(key, value);
        MemTableCell cell = uiMemList.get(addr);
        cell.setData(value);
        cell.setDecData((int)registerWriteData);
        uiMemList.set(addr, cell);
    }
}