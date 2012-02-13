package edu.lmu.cs.xlg.manatee.entities;

public class PlainLoop extends Statement {

    private Block body;

    public PlainLoop(Block body) {
        this.body = body;
    }

    public Block getBody() {
        return body;
    }
}
