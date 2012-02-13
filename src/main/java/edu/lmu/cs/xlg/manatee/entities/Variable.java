package edu.lmu.cs.xlg.manatee.entities;

public class Variable extends Declaration {

    private String typename;
    private Expression initializer;
    private boolean constant;
    private Integer level;

    /**
     * Constructs a variable.
     */
    public Variable(String name, String typename, Expression initializer, boolean constant) {
        super(name);
        this.typename = typename;
        this.initializer = initializer;
        this.constant = constant;
    }

    /**
     * Returns the initializer.
     */
    public Expression getInitializer() {
        return initializer;
    }

    /**
     * Returns the typename.
     */
    public String getTypename() {
        return typename;
    }

    /**
     * Returns whether this variable is readonly.
     */
    public boolean isConstant() {
        return constant;
    }
}
