package edu.lmu.cs.xlg.manatee.entities;

public class VariableReference extends Expression {

    private String name;

    public VariableReference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
