package edu.lmu.cs.xlg.manatee.entities;

public class BinaryExpression extends Expression {

    private String op;
    private Expression left;
    private Expression right;

    /**
     * Creates a binary expression for a given operator and operands.
     */
    public BinaryExpression(Expression left, String op, Expression right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    /**
     * Returns the left operand.
     */
    public Expression getLeft() {
        return left;
    }

    /**
     * Returns the operator as a string.
     */
    public String getOp() {
        return op;
    }

    /**
     * Returns the right operand.
     */
    public Expression getRight() {
        return right;
    }
}
