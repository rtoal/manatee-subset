package edu.lmu.cs.xlg.manatee.entities;

import java.util.List;

/**
 * A Manatee script. A script is really just a top-level block.
 */
public class Script extends Block {

    public Script(List<Statement> statements) {
        super(statements);
    }
}
