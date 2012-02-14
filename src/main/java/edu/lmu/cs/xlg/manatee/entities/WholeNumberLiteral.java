package edu.lmu.cs.xlg.manatee.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * A literal for 32-bit integers.
 */
public class WholeNumberLiteral extends Literal {

    // Semantic value, not part of syntax analysis.
    private Integer value;

    public WholeNumberLiteral(String lexeme) {
        super(lexeme);
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        type = Type.WHOLE_NUMBER;
        try {
            value = Integer.valueOf(getLexeme());
        } catch (NumberFormatException e) {
            log.error("bad.int", getLexeme());
        }
    }
}
