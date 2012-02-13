package edu.lmu.cs.xlg.manatee.entities;

import java.util.List;

public class Procedure extends Declaration {

    private List<Variable> parameters;
    private Block body;

    public Procedure(String name, List<Variable> parameters, Block body) {
        super(name);
        this.parameters = parameters;
        this.body = body;
    }

    public List<Variable> getParameters() {
        return parameters;
    }

    public Block getBody() {
        return body;
    }
}
