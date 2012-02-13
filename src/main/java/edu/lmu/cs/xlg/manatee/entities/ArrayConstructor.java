package edu.lmu.cs.xlg.manatee.entities;

import java.util.List;

public class ArrayConstructor extends Expression {

    private List<Expression> expressions;

    public ArrayConstructor(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }
}
