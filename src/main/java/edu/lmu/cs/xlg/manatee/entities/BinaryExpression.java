package edu.lmu.cs.xlg.manatee.entities;

import edu.lmu.cs.xlg.util.Log;

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

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        left.analyze(log, table, owner, inLoop);
        right.analyze(log, table, owner, inLoop);

        // num op num (for arithmetic op)
        if (op.matches("[-+*/]")) {
            left.assertArithmetic(op, log);
            right.assertArithmetic(op, log);
            type = (left.type == Type.NUMBER || right.type == Type.NUMBER)
                ? Type.NUMBER : Type.WHOLE_NUMBER;

        // int op int returning int (for shifts and mod)
        } else if (op.matches("<<|>>")) {
            left.assertInteger(op, log);
            right.assertInteger(op, log);
            type = Type.WHOLE_NUMBER;

        // char/num/str op char/num/str (for greater/less inequalities)
        } else if (op.matches("<|<=|>|>=")) {
            if (left.type == Type.CHARACTER) {
                right.assertChar(op, log);
            } else if (left.type == Type.STRING) {
                right.assertString(op, log);
            } else if (left.type.isArithmetic()){
                left.assertArithmetic(op, log);
                right.assertArithmetic(op, log);
            }
            type = Type.TRUTH_VALUE;

        // equals or not equals on primitives
        } else if (op.matches("=|≠")) {
            if (!(left.type.isPrimitive() && (left.isCompatibleWith(right.type) || right.isCompatibleWith(left.type)))) {
                log.error("eq.type.error", op, left.type.getName(), right.type.getName());
            }
            type = Type.TRUTH_VALUE;

        // bool and bool
        // bool or bool
        } else if (op.matches("and|or")) {
            left.assertBoolean(op, log);
            right.assertBoolean(op, log);
            type = Type.TRUTH_VALUE;

        } else {
            log.error("internal.error.bad.operator");
        }
    }
}
