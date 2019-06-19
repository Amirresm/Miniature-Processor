package control.models.cpu;

public class ControlUnit {
    Signal REGDES;
    Signal REGWRITE;
    Signal ALUSRC;
    Signal BRANCH;
    Signal JUMP;
    Signal MEMTOREG;
    Signal MEMREAD;
    Signal ALUOP;
    Signal MEMWRITE;
    Signal HALT;

    public ControlUnit() {
        REGDES = new Signal(0);
        REGWRITE = new Signal(0);
        ALUOP = new Signal(0);
        ALUSRC = new Signal(0);
        BRANCH = new Signal(0);
        JUMP = new Signal(0);
        MEMREAD = new Signal(0);
        MEMWRITE = new Signal(0);
        MEMTOREG = new Signal(0);
        HALT = new Signal(0);
    }

    public void setup(String oppCode) {
        resetSignals();
        switch (oppCode) {
            case "0000":        //add
                ALUOP.data = 1;
                REGDES.data = 1;
                REGWRITE.data = 1;
                break;
            case "0001":        //sub
                ALUOP.data = 2;
                REGDES.data = 1;
                REGWRITE.data = 1;
                break;
            case "0010":        //slt
                ALUOP.data = 3;
                REGDES.data = 1;
                REGWRITE.data = 1;
                break;
            case "0011":        //or
                ALUOP.data = 4;
                REGDES.data = 1;
                REGWRITE.data = 1;
                break;
            case "0100":        //and
                ALUOP.data = 5;
                REGDES.data = 1;
                REGWRITE.data = 1;
                break;
            case "1001":        //lw
                ALUOP.data = 1; //add
                ALUSRC.data = 1;
                MEMTOREG.data = 1;
                MEMREAD.data = 1;
                REGWRITE.data = 1;
                break;
            case "1010":        //sw
                ALUOP.data = 1; //add
                ALUSRC.data = 1;
                MEMWRITE.data = 1;
                break;
            case "1011":        //beq
                ALUOP.data = 2; //SUB
                BRANCH.data = 1;
                break;
            case "1101":        //jump
                JUMP.data = 1;
                break;
            case "1110":        //halt
                HALT.data = 1;
                break;
        }
    }
    private void resetSignals() {
        REGDES.data = 0;
        REGWRITE.data = 0;
        ALUOP.data = 0;
        ALUSRC.data = 0;
        BRANCH.data = 0;
        JUMP.data = 0;
        MEMREAD.data = 0;
        MEMWRITE.data = 0;
        MEMTOREG.data = 0;
        HALT.data = 0;
    }
}