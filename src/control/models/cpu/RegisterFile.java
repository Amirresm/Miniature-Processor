package control.models.cpu;

import control.models.MemTableCell;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RegisterFile {
    private Map<String, String> registerMemory = new HashMap<>();

    public Set<Integer> used = new HashSet<>();

    private String readAddr1;
    private String readAddr2;
    private String writeAddr;
    private boolean regWrite = Boolean.FALSE;

    private ObservableList<MemTableCell> uiMemList;
    private int memSize;

    public RegisterFile(ObservableList<MemTableCell> uiMemList) {
        this.uiMemList = uiMemList;
        resizeMemory(16);
    }

    public void resizeMemory(int memorySize) {
        this.memSize = memorySize;
        if (memorySize < 0)
            memorySize = 0;
        if (memorySize > 16)
            memorySize = 16;

        int radix = (int) Math.sqrt(memorySize - 1) + 1;
        uiMemList.clear();
        for (int i = 0; i < memorySize; i++) {
            String internalKey = Utility.decimalToString(i, 4);
            String uiKey = Utility.decimalToString(i, radix);
            String value = "00000000000000000000000000000000";
            registerMemory.put(internalKey, value);
            MemTableCell cell = new MemTableCell(i, uiKey, value);
            cell.setDecData(0);
            uiMemList.add(cell);
        }
        used.clear();
    }

    void setup(String readAddr1, String readAddr2, String writeAddr, Signal flag) {
        this.readAddr1 = readAddr1;
        this.readAddr2 = readAddr2;
        this.writeAddr = writeAddr;
        regWrite = flag.data == 1;
    }

    String getFirstData() {
        return registerMemory.get(readAddr1);
    }

    String getSecondData() {
        return registerMemory.get(readAddr2);
    }

    void write(String registerWriteData) {
        if (regWrite && registerWriteData.length() == 32) {
            putInMemory(writeAddr, registerWriteData);
        }
    }

    public int getMemSize() {
        return memSize;
    }

    private void putInMemory(String addr, String registerWriteData) {
        if (registerMemory.containsKey(addr))
            registerMemory.replace(addr, registerWriteData);
        else
            registerMemory.put(addr,registerWriteData);
        int tableIndex = Integer.parseInt(addr, 2);
        MemTableCell cell = uiMemList.get(tableIndex);
        cell.setData(registerWriteData);
        cell.setDecData((int)Long.parseLong(registerWriteData, 2));
        uiMemList.set(tableIndex, cell);
        used.add(tableIndex);
    }

}