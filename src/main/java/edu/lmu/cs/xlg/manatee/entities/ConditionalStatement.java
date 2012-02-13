package edu.lmu.cs.xlg.manatee.entities;

import java.util.List;

public class ConditionalStatement extends Statement {

    public static class Arm extends Entity {
        Expression guard;
        Block block;

        public Arm(Expression guard, Block block) {
            this.guard = guard;
            this.block = block;
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
}
