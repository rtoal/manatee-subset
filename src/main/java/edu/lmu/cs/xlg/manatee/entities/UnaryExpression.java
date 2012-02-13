package edu.lmu.cs.xlg.manatee.entities;

public class UnaryExpression extends Expression {

    private String op;
    private Expression operand;

    /**
     * Creates a unary expression.
     */
    public UnaryExpression(String op, Expression operand) {
        this.op = op;
        this.operand = operand;
    }

    /**
     * Returns the operator.
     */
    public String getOp() {
        return op;
    }

    /**
     * Returns the operand.
     */
    public Expression getOperand() {
        return operand;
    }
}
