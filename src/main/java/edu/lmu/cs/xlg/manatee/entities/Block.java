package edu.lmu.cs.xlg.manatee.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * A Manatee block.
 */
public class Block extends Entity {

    private List<Statement> statements = new ArrayList<Statement>();

    public Block(List<Statement> statements) {
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }
}
