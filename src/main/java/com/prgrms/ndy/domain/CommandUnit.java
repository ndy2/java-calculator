package com.prgrms.ndy.domain;

import com.prgrms.ndy.domain.operation.Opcode;

import java.util.ArrayList;
import java.util.List;

public class CommandUnit implements Command {

    private int size;
    private List<? super Number> numbers = new ArrayList<>();
    private List<Opcode> ops = new ArrayList<>();

    public static Command of(String expression) {
        CommandUnit commandUnit = new CommandUnit();
        String[] tokens = expression.split(" ");
        for (int i = 0; i < tokens.length; i++) {
            if (i % 2 == 0) {
                commandUnit.addNumber(getNumber(tokens[i]));
            } else {
                Opcode opcode = Opcode.of(tokens[i].charAt(0))
                        .orElseThrow(() -> new IllegalStateException("DB에 이상한 OP 코드가 들어있습니다."));
                commandUnit.addOpcode(opcode);
            }
        }
        return commandUnit;
    }


    public <T extends Number> void addNumber(T number) {
        numbers.add(number);
        size += 1;
    }

    public void addOpcode(Opcode opcode) {
        ops.add(opcode);
    }

    public int getSize() {
        return size;
    }

    @Override
    public Number proc() {
        return 0;
    }

    @Override
    public String toString() {
        if (size == 0) return "{Empty Command}";

        StringBuilder sb = new StringBuilder();
        int i;
        for (i = 0; i < size - 1; i++) {
            sb.append(numbers.get(i)).append(' ');
            sb.append(ops.get(i).getCode()).append(' ');
        }
        sb.append(numbers.get(i));
        return sb.toString();
    }

    public static Number getNumber(String in) {
        if (in.contains(".")) {
            return Double.valueOf(in);
        } else {
            return Long.valueOf(in);
        }
    }
}