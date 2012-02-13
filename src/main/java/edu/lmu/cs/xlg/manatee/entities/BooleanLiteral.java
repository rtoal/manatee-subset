package edu.lmu.cs.xlg.manatee.entities;

/**
 * A simple typsafe enum of the two boolean literals, yes and no.
 */
public class BooleanLiteral extends Literal {

    public static final BooleanLiteral TRUE = new BooleanLiteral("yes");
    public static final BooleanLiteral FALSE = new BooleanLiteral("no");

    /**
     * Constructs a boolean literal.  The constructor is private because the only two instances
     * of this class are defined as public static members.
     */
    private BooleanLiteral(String lexeme) {
        super(lexeme);
    }
}