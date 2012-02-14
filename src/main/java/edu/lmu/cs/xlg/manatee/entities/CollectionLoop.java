package edu.lmu.cs.xlg.manatee.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * A loop that runs an iterator through the values in an array or string.
 */
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

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        collection.analyze(log, table, owner, inLoop);
        collection.assertArray("loop", log);
        body.createTable(table);
        body.getTable().insert(new Variable(iteratorName, collection.getType().array()), log);
        body.analyze(log, table, owner, true);
    }
}
