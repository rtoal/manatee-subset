package edu.lmu.cs.xlg.manatee.generators;

import java.io.PrintWriter;

import edu.lmu.cs.xlg.manatee.entities.Block;
import edu.lmu.cs.xlg.manatee.entities.Script;

/**
 * A generator that translates an Manatee program into JavaScript.
 */
public class ManateeToJavaScriptGenerator extends Generator {

    @Override
    public void generate(Script script, PrintWriter writer) {
        this.writer = writer;

        emit("(function () {");
        generateBlock(script);
        emit("}());");
    }

    /**
     * Emits JavaScript code for the given Manatee block.
     */
    private void generateBlock(Block block) {
        indentLevel++;

        // TODO: STUB
        emit("/* THIS GENERATOR IS NOT IMPLEMENTED */");

        indentLevel--;
    }
}
