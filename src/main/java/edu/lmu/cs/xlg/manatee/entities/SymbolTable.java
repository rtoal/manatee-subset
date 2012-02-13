package edu.lmu.cs.xlg.manatee.entities;

/**
 * An Manatee symbol table.
 */
public class SymbolTable {

    private SymbolTable parent;

    /**
     * Creates a symbol table with the given parent.
     */
    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
    }

    /**
     * Creates a root symbol table.
     */
    public SymbolTable() {
        this(null);
    }

    /**
     * Returns the parent table.
     */
    public SymbolTable getParent() {
        return parent;
    }
}
