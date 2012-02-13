package edu.lmu.cs.xlg.manatee.entities;

import java.util.List;

public class FunctionCall extends Expression {

    private Expression function;
    private List<Expression> args;

    public FunctionCall(Expression function, List<Expression> args) {
        this.function = function;
        this.args = args;
    }

    public Expression getFunction() {
        return function;
    }

    public List<Expression> getArgs() {
        return args;
    }
}
