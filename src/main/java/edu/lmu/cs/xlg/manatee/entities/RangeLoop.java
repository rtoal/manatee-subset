package edu.lmu.cs.xlg.manatee.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * A loop that takes an iterator from one value to another by some step value.
 */
public class RangeLoop extends Statement {

    private String iteratorName;
    private Expression low;
    private Expression high;
    private Expression step;
    private Block body;

    public RangeLoop(String iteratorName, Expression low, Expression high, Expression step,
            Block body) {
        this.iteratorName = iteratorName;
        this.low = low;
        this.high = high;
        this.step = step;
        this.body = body;
    }

    public String getIteratorName() {
        return iteratorName;
    }

    public Expression getLow() {
        return low;
    }

    public Expression getHigh() {
        return high;
    }

    public Expression getStep() {
        return step;
    }

    public Block getBody() {
        return body;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        low.analyze(log, table, owner, inLoop);
        high.analyze(log, table, owner, inLoop);
        step.analyze(log, table, owner, inLoop);
        low.assertInteger("range loop", log);
        high.assertInteger("range loop", log);
        step.assertInteger("range loop", log);
        body.createTable(table);
        body.getTable().insert(new Variable(iteratorName, Type.WHOLE_NUMBER), log);
        body.analyze(log, table, owner, true);
    }
}
