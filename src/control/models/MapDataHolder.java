package control.models;

public class MapDataHolder {
    public StatsHolder statsHolder = new StatsHolder();

    public String pc;
    public String pcPOne;
    public String instruction;
    public String jump;
    public String oppCode;
    public String rs;
    public String rt;
    public String rd;
    public String offset;
    public String regRead1;
    public String regRead2;
    public String writeReg;
    public String readData1;
    public String readData2;
    public String aluResult;
    public String branchPc;
    public String nextPc;
    public String memoryWrite;
    public String memory;
    public String writeData;

    public boolean regDest;
    public boolean jumpSignal;
    public boolean branch;
    public boolean memToReg;
    public boolean memWrite;
    public boolean memRead;
    public boolean aluSrc;
    public boolean regWrite;
    public boolean aluZero;
    public boolean branchANDZero;

    public String aluOp;

    public MapDataHolder setPc(String pc) {
        this.pc = Long.parseLong(pc, 2)+"";
        return this;
    }

    public MapDataHolder setPcPOne(String pcPOne) {
        this.pcPOne = String.valueOf(Long.parseLong(pcPOne, 2));
        return this;
    }

    public MapDataHolder setInstruction(String instruction) {
        this.instruction = Long.parseLong(instruction, 2)+"";
        return this;
    }

    public MapDataHolder setJump(String jump) {
        this.jump = Long.parseLong(jump, 2)+"";
        return this;
    }

    public MapDataHolder setOppCode(String oppCode) {
        this.oppCode = Long.parseLong(oppCode, 2)+"";
        return this;
    }

    public MapDataHolder setRs(String rs) {
        this.rs = Long.parseLong(rs, 2)+"";
        return this;
    }

    public MapDataHolder setRt(String rt) {
        this.rt = Long.parseLong(rt, 2)+"";
        return this;
    }

    public MapDataHolder setRd(String rd) {
        this.rd = Long.parseLong(rd, 2)+"";
        return this;
    }

    public MapDataHolder setOffset(String offset) {
        this.offset = Long.parseLong(offset, 2)+"";
        return this;
    }

    public MapDataHolder setRegRead1(String regRead1) {
        this.regRead1 = Long.parseLong(regRead1, 2)+"";
        return this;
    }

    public MapDataHolder setRegRead2(String regRead2) {
        this.regRead2 = Long.parseLong(regRead2, 2)+"";
        return this;
    }

    public MapDataHolder setWriteReg(String writeReg) {
        this.writeReg = Long.parseLong(writeReg, 2)+"";
        return this;
    }

    public MapDataHolder setReadData1(String readData1) {
        this.readData1 = Long.parseLong(readData1, 2)+"";
        return this;
    }

    public MapDataHolder setReadData2(String readData2) {
        this.readData2 = Long.parseLong(readData2, 2)+"";
        return this;
    }

    public MapDataHolder setAluResult(String aluResult) {
        this.aluResult = Long.parseLong(aluResult, 2)+"";
        return this;
    }

    public MapDataHolder setBranchPc(String branchPc) {
        this.branchPc = Long.parseLong(branchPc, 2)+"";
        return this;
    }

    public MapDataHolder setNextPc(String nextPc) {
        this.nextPc = Long.parseLong(nextPc, 2)+"";
        return this;
    }

    public MapDataHolder setMemoryWrite(String memoryWrite) {
        this.memoryWrite = Long.parseLong(memoryWrite, 2)+"";
        return this;
    }

    public MapDataHolder setMemory(String memory) {
        if(memory == "0")
            this.memory = "0";
        else
            this.memory = Long.parseLong(memory, 2)+"";
        return this;
    }

    public MapDataHolder setWriteData(String writeData) {
        this.writeData = Long.parseLong(writeData, 2)+"";
        return this;
    }

    public void reset() {
         pc = "PC";
        pcPOne = "PC+1";
        instruction = "00000000000000000000000000000000";
        jump = "Jump";
        oppCode = "Oppcode";
        rs = "RS";
        rt = "RT";
        rd = "RD";
        offset = "Offset";
        regRead1 = "ReadReg1";
        regRead2= "ReadReg2";
        writeReg = "WriteReg";
        readData1 = "ReadData1";
        readData2 = "ReadData2";
         aluResult = "ALUresult";
         branchPc = "BranchPC";
         nextPc = "NextPC";
        memoryWrite = "MemoryWrite";
         memory = "Memory";
         writeData = "WriteData";

         regDest = false;
         jumpSignal = false;
         branch = false;
         memToReg = false;
         memWrite = false;
         memRead = false;
         aluSrc = false;
         regWrite = false;
         aluZero = false;
         branchANDZero = false;

         aluOp = "ALUOp";
    }
}
