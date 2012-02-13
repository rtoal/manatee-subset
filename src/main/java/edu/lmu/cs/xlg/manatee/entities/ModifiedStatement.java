package edu.lmu.cs.xlg.manatee.entities;

public class ModifiedStatement extends Statement {

    public static enum ModifierType {IF, WHILE};

    public static class Modifier {
        ModifierType type;
        Expression condition;

        public Modifier(ModifierType type, Expression condition) {
            this.type = type;
            this.condition = condition;
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
}
