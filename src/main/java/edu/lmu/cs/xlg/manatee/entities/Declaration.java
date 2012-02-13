package edu.lmu.cs.xlg.manatee.entities;

public abstract class Declaration extends Statement {

    private String name;

    public Declaration(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
