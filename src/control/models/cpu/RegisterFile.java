package control.models.cpu;

import control.models.MemTableCell;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class RegisterFile {
    public Map<String, String> registerMemory = new HashMap<>();

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
            uiMemList.add(cell);
        }
    }

    public void setup(String readAddr1, String readAddr2, String writeAddr, Signal flag) {
        this.readAddr1 = readAddr1;
        this.readAddr2 = readAddr2;
        this.writeAddr = writeAddr;
        regWrite = flag.data == 1 ? true : false;
    }

    public String getFirstData() {
        return registerMemory.get(readAddr1);
    }

    public String getSecondData() {
        return registerMemory.get(readAddr2);
    }

    public void write(String registerWriteData) {
        if (regWrite && registerWriteData.length() == 32) {
//            registerMemory.replace(writeAddr, registerWriteData);
//            int tableIndex = Integer.parseInt(writeAddr, 2);
//            MemTableCell cell = uiMemList.get(tableIndex);
//            cell.setData(registerWriteData);
//            uiMemList.set(tableIndex, cell);
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
        uiMemList.set(tableIndex, cell);
    }
}