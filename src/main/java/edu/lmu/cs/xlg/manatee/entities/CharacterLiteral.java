package edu.lmu.cs.xlg.manatee.entities;

public class CharacterLiteral extends Literal {

    /**
     * Creates a character literal given its lexeme. The lexeme does contain the single quote
     * delimiters.
     */
    public CharacterLiteral(String lexeme) {
        super(lexeme);
    }
}
