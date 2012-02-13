package edu.lmu.cs.xlg.manatee.entities;

public class CollectionLoop extends Statement {

    private String iteratorName;
    private Expression collection;
    private Block body;

    public CollectionLoop(String iteratorName, Expression collection, Block body) {
        this.iteratorName = iteratorName;
        this.collection = collection;
        this.body = body;
    }

    public String getIteratorName() {
        return iteratorName;
    }

    public Expression getCollection() {
        return collection;
    }

    public Block getBody() {
        return body;
    }
}
