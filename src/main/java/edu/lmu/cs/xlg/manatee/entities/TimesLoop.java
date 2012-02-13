package edu.lmu.cs.xlg.manatee.entities;

public class TimesLoop extends Statement {

    private Expression count;
    private Block body;

    public TimesLoop(Expression count, Block body) {
        this.count = count;
        this.body = body;
    }

    public Expression getCount() {
        return count;
    }

    public Block getBody() {
        return body;
    }
}
