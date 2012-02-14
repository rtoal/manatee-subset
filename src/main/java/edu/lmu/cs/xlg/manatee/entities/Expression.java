package edu.lmu.cs.xlg.manatee.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * An expression.
 */
public abstract class Expression extends Entity {

    // As the language is statically typed, we can compute and store the type at compile time.
    Type type;

    /**
     * Returns the type of this expression.
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns whether this expression is compatible with (that is, "can be assigned to an object
     * of") a given type.
     */
    public boolean isCompatibleWith(Type testType) {
        return this.type == testType
            || this.type == Type.WHOLE_NUMBER && testType == Type.NUMBER
            || this.type == Type.NULL_TYPE && testType.isReference()
            || this.type == Type.ARBITRARY
            || testType == Type.ARBITRARY;
    }

    /**
     * Returns whether this expression can be assigned to.  False by default; it will be
     * overwritten only in those particular cases where it should return true.
     */
    boolean isWritableLValue() {
        return false;
    }

    // Helpers for semantic analysis, called from the analyze methods of other expressions.  These
    // are by no means necessary, but they are very convenient.

    void assertAssignableTo(Type otherType, Log log, String errorKey) {
        if (!this.isCompatibleWith(otherType)) {
            log.error(errorKey, otherType.getName(), this.type.getName());
        }
    }

    void assertArithmetic(String context, Log log) {
        if (!(type == Type.WHOLE_NUMBER || type == Type.NUMBER)) {
            log.error("non.arithmetic", context);
        }
    }

    void assertArithmeticOrChar(String context, Log log) {
        if (!(type == Type.WHOLE_NUMBER || type == Type.NUMBER || type == Type.CHARACTER)) {
            log.error("non.arithmetic.or.char", context);
        }
    }

    void assertInteger(String context, Log log) {
        if (!(type == Type.WHOLE_NUMBER)) {
            log.error("non.integer", context);
        }
    }

    void assertBoolean(String context, Log log) {
        if (!(type == Type.TRUTH_VALUE)) {
            log.error("non.boolean", context, type);
        }
    }

    void assertChar(String context, Log log) {
        if (!(type == Type.CHARACTER)) {
            log.error("non.char", context);
        }
    }

    void assertArray(String context, Log log) {
        if (!(type instanceof ArrayType)) {
            log.error("non.array", context);
        }
    }

    void assertString(String context, Log log) {
        if (!(type == Type.STRING)) {
            log.error("non.string", context);
        }
    }

    void assertArrayOrString(String context, Log log) {
        if (!(type == Type.STRING || type instanceof ArrayType)) {
            log.error("non.array.or.string", context);
        }
    }
}