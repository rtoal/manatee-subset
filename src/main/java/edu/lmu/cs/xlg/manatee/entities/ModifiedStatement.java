package edu.lmu.cs.xlg.manatee.entities;

import edu.lmu.cs.xlg.util.Log;

public class ModifiedStatement extends Statement {

    public static enum ModifierType {IF, WHILE};

    public static class Modifier extends Entity {
        ModifierType type;
        Expression condition;

        public Modifier(ModifierType type, Expression condition) {
            this.type = type;
            this.condition = condition;
        }

        @Override
        public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
            condition.analyze(log, table, owner, inLoop);
        }
    }

    private Modifier modifier;
    private Statement statement;

    public ModifiedStatement(Modifier modifier, Statement statement) {
        this.modifier = modifier;
        this.statement = statement;
    }

    public Modifier getModifier() {
        return modifier;
    }

    public Statement getStatement() {
        return statement;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        modifier.analyze(log, table, owner, inLoop);
        statement.analyze(log, table, owner, inLoop);
    }
}
