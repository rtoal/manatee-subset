package edu.lmu.cs.xlg.manatee.entities;

import java.util.List;

public class CallStatement extends Statement {

    private String procedureName;
    private List<Expression> args;

    public CallStatement(String procedureName, List<Expression> args) {
        this.procedureName = procedureName;
        this.args = args;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public List<Expression> getArgs() {
        return args;
    }
}
