package edu.lmu.cs.xlg.manatee.entities;

/**
 * A singleton class for an object representing the literal <code>nothing</code>.
 */
public class NullLiteral extends Literal {

    public static NullLiteral INSTANCE = new NullLiteral();

    private NullLiteral() {
        super("nothing");
    }
}
