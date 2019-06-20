package control.models.cpu;

import java.util.*;

public class InstructionMem {
    final static int bits = 16;
    public Map<String, String> instructionMemory = new HashMap<>();

    public String getInstruction(String pc) {
        long pcLong = Long.parseLong(pc, 2);
        return instructionMemory.get(Utility.decimalToString(pcLong, bits));
    }

    public void load(String codeText) {
        Scanner scanner = new Scanner(codeText);
        List<String> instructions = new ArrayList<>();
        while (scanner.hasNextLine()) {
            int value = Integer.parseInt(scanner.nextLine());
            instructions.add(String.format("%" + 32 + "s", Integer.toBinaryString(value)).replace(' ', '0'));
        }
        for (int i = 0; i < instructions.size(); i++) {
            String key = Utility.decimalToString(i, bits);
            instructionMemory.put(key, instructions.get(i));
        }
        scanner.close();
    }
}