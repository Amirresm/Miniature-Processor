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

    public RegisterFile() {
        registerMemory.put("0000", "00000000000000000000000000000000");
        registerMemory.put("0001", "00000000000000000000000000000000");
        registerMemory.put("0010", "00000000000000000000000000000000");
        registerMemory.put("0011", "00000000000000000000000000000000");
        registerMemory.put("0100", "00000000000000000000000000000000");
        registerMemory.put("0101", "00000000000000000000000000000000");
        registerMemory.put("0110", "00000000000000000000000000000000");
        registerMemory.put("0111", "00000000000000000000000000000000");
        registerMemory.put("1000", "00000000000000000000000000000000");
        registerMemory.put("1001", "00000000000000000000000000000000");
        registerMemory.put("1010", "00000000000000000000000000000000");
        registerMemory.put("1011", "00000000000000000000000000000000");
        registerMemory.put("1100", "00000000000000000000000000000000");
        registerMemory.put("1101", "00000000000000000000000000000000");
        registerMemory.put("1110", "00000000000000000000000000000000");
        registerMemory.put("1111", "00000000000000000000000000000000");
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
            registerMemory.replace(writeAddr, registerWriteData);
            int tableIndex = Integer.parseInt(writeAddr, 2);
            MemTableCell cell = uiMemList.get(tableIndex);
            cell.setData(registerWriteData);
            uiMemList.set(tableIndex, cell);
        }
    }

    public ObservableList<MemTableCell> getUiMemList() {
        return uiMemList;
    }

    public void setUiMemList(ObservableList<MemTableCell> uiMemList) {
        this.uiMemList = uiMemList;
    }
}