package edu.lmu.cs.xlg.manatee.entities;

public class SubscriptExpression extends Expression {

    private Expression base;
    private Expression subscript;

    public SubscriptExpression(Expression base, Expression subscript) {
        this.base = base;
        this.subscript = subscript;
    }

    public Expression getBase() {
        return base;
    }

    public Expression getSubscript() {
        return subscript;
    }
}
