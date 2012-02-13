package edu.lmu.cs.xlg.manatee.entities;

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
}
