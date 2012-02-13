package edu.lmu.cs.xlg.manatee.entities;

import java.util.List;

public class Function extends Declaration {

    private String returnTypeName;
    private List<Variable> parameters;
    private Block body;

    /**
     * Creates a function object.
     */
    public Function(String returnTypeName, String name, List<Variable> parameters, Block body) {
        super(name);
        this.returnTypeName = returnTypeName;
        this.parameters = parameters;
        this.body = body;
    }

    public String getReturnTypeName() {
        return returnTypeName;
    }

    public List<Variable> getParameters() {
        return parameters;
    }

    public Block getBody() {
        return body;
    }
}
