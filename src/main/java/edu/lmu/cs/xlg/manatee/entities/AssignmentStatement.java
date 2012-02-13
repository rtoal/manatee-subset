package edu.lmu.cs.xlg.manatee.entities;

public class AssignmentStatement extends Statement {

    private Expression target;
    private Expression source;

    public AssignmentStatement(Expression target, Expression source) {
        this.target = target;
        this.source = source;
    }

    public Expression getTarget() {
        return target;
    }

    public Expression getSource() {
        return source;
    }
}
