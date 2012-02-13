package edu.lmu.cs.xlg.manatee.entities;

public class WriteStatement extends Statement {

    private Expression expression;

    public WriteStatement(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
