package edu.lmu.cs.xlg.manatee.entities;

import java.util.List;

import edu.lmu.cs.xlg.util.Log;

public class ConditionalStatement extends Statement {

    public static class Arm extends Entity {
        Expression guard;
        Block block;

        public Arm(Expression guard, Block block) {
            this.guard = guard;
            this.block = block;
        }

        @Override
        public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
            guard.analyze(log, table, owner, inLoop);
            guard.assertBoolean("condition", log);
            block.analyze(log, table, owner, inLoop);
        }
    }

    private List<Arm> arms;
    private Block elsePart;

    public ConditionalStatement(List<Arm> arms, Block elsePart) {
        this.arms = arms;
        this.elsePart = elsePart;
    }

    public List<Arm> getArms() {
        return arms;
    }

    public Block getElsePart() {
        return elsePart;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        for (Arm arm: arms) {
            arm.analyze(log, table, owner, inLoop);
        }
        if (elsePart != null) {
            elsePart.analyze(log, table, owner, inLoop);
        }
    }
}
