package edu.lmu.cs.xlg.manatee.entities;

public class ReadStatement extends Statement {

    private Expression expression;

    public ReadStatement(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }
}
