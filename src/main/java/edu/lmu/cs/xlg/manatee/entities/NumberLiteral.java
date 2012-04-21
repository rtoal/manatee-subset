package edu.lmu.cs.xlg.manatee.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * A number literal, like "2.3", "4.6634E-231", etc.
 */
public class NumberLiteral extends Literal {

    private Double value;

    /**
     * Creates a real literal from its lexeme.
     */
    public NumberLiteral(String lexeme) {
        super(lexeme);
    }

    /**
     * Returns the value.
     */
    public Double getValue() {
        return value;
    }

    /**
     * Analyzes this literal, determining its value.
     */
    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        type = Type.NUMBER;
        String lexeme = getLexeme();
        try {
            if (lexeme.contains("^")) {
                System.out.println("Before: " + lexeme + "of len " + lexeme.length());
                lexeme = lexeme.replaceAll("(x|\\xd7)10\\^", "E");
                System.out.println("After: " + lexeme);
            }
            value = Double.valueOf(lexeme);
        } catch (NumberFormatException e) {
            log.error("bad_number", getLexeme());
        }
    }
}
