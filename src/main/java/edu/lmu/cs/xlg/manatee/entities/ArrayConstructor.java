package edu.lmu.cs.xlg.manatee.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.lmu.cs.xlg.util.Log;

/**
 * An array expression, such as [3.50, 9, -6], or [].
 */
public class ArrayConstructor extends Expression {

    private List<Expression> expressions;

    public ArrayConstructor(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {

        // First analyze the subexpressions
        for (Expression e: expressions) {
            e.analyze(log, table, owner, inLoop);
        }

        // Now lets get the type of the array expression. Begin by creating a set of all of the
        // expressions in the array itself.
        Set<Type> types = new HashSet<Type>();
        for (Expression e: expressions) {
            types.add(e.getType());
        }

        // Now see if we can make any sense out of the array of types.
        if (types.isEmpty()) {
            type = Type.ARBITRARY_ARRAY;

        } else if (types.size() == 1) {
            if (types.contains(Type.NULL_TYPE)) {
                // Nothing but n00bs in the array
                type = Type.ARBITRARY_ARRAY_OF_REFERENCES;
            } else {
                // Exactly one type represented -- good deal
                type = types.toArray(new Type[0])[0].array();
            }

        } else if (types.size() == 2 && types.contains(Type.NUMBER) && types.contains(Type.WHOLE_NUMBER)) {
            // Only NUMBRs and INTs -- okay too
            type = Type.NUMBER.array();

        } else {
            // Can't make sense out of it because of conflicts
            log.error("bad.array.expression");
            type = Type.ARBITRARY;
        }
    }
}
