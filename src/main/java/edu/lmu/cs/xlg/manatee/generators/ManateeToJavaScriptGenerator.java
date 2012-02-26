package edu.lmu.cs.xlg.manatee.generators;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import edu.lmu.cs.xlg.manatee.entities.AssignmentStatement;
import edu.lmu.cs.xlg.manatee.entities.Block;
import edu.lmu.cs.xlg.manatee.entities.BooleanLiteral;
import edu.lmu.cs.xlg.manatee.entities.CallStatement;
import edu.lmu.cs.xlg.manatee.entities.CharacterLiteral;
import edu.lmu.cs.xlg.manatee.entities.CollectionLoop;
import edu.lmu.cs.xlg.manatee.entities.ConditionalStatement;
import edu.lmu.cs.xlg.manatee.entities.Declaration;
import edu.lmu.cs.xlg.manatee.entities.DoNothingStatement;
import edu.lmu.cs.xlg.manatee.entities.ExitStatement;
import edu.lmu.cs.xlg.manatee.entities.Expression;
import edu.lmu.cs.xlg.manatee.entities.Literal;
import edu.lmu.cs.xlg.manatee.entities.ModifiedStatement;
import edu.lmu.cs.xlg.manatee.entities.NullLiteral;
import edu.lmu.cs.xlg.manatee.entities.NumberLiteral;
import edu.lmu.cs.xlg.manatee.entities.PlainLoop;
import edu.lmu.cs.xlg.manatee.entities.RangeLoop;
import edu.lmu.cs.xlg.manatee.entities.ReadStatement;
import edu.lmu.cs.xlg.manatee.entities.ReturnStatement;
import edu.lmu.cs.xlg.manatee.entities.Script;
import edu.lmu.cs.xlg.manatee.entities.Statement;
import edu.lmu.cs.xlg.manatee.entities.StringLiteral;
import edu.lmu.cs.xlg.manatee.entities.Subroutine;
import edu.lmu.cs.xlg.manatee.entities.TimesLoop;
import edu.lmu.cs.xlg.manatee.entities.Variable;
import edu.lmu.cs.xlg.manatee.entities.WhileLoop;
import edu.lmu.cs.xlg.manatee.entities.WholeNumberLiteral;
import edu.lmu.cs.xlg.manatee.entities.WriteStatement;

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

        for (Statement s: block.getStatements()) {
            generateStatement(s);
        }

        indentLevel--;
    }

    /**
     * Emits JavaScript code for the given statement.
     */
    private void generateStatement(Statement s) {
        if (s instanceof Declaration){
            generateDeclaration(Declaration.class.cast(s));

        } else if (s instanceof DoNothingStatement) {
            emit(";");

        } else if (s instanceof AssignmentStatement) {
            AssignmentStatement a = AssignmentStatement.class.cast(s);
            emit(String.format("%s = %s;",
                    generateExpression(a.getTarget()),
                    generateExpression(a.getSource())));

        } else if (s instanceof ReadStatement) {
            // TODO

        } else if (s instanceof WriteStatement) {
            Expression e = WriteStatement.class.cast(s).getExpression();
            emit("console.log(" + generateExpression(e) + ");");

        } else if (s instanceof ExitStatement) {
            emit("break;");

        } else if (s instanceof ReturnStatement) {
            Expression e = ReturnStatement.class.cast(s).getExpression();
            emit("return" + (e == null ? "" : " " + generateExpression(e)) + ";");

        } else if (s instanceof CallStatement){
            // TODO

        } else if (s instanceof ModifiedStatement) {
            // TODO

        } else if (s instanceof ConditionalStatement) {
            // TODO

        } else if (s instanceof PlainLoop) {
            emit("while (true) {");
            generateBlock(PlainLoop.class.cast(s).getBody());
            emit("}");

        } else if (s instanceof TimesLoop) {
            // TODO

        } else if (s instanceof CollectionLoop) {
            // TODO

        } else if (s instanceof RangeLoop) {
            // TODO

        } else if (s instanceof WhileLoop) {
            WhileLoop w = WhileLoop.class.cast(s);
            emit("while (" + generateExpression(w.getCondition()) + ") {");
            generateBlock(w.getBody());
            emit("}");
        }
    }

    private String generateExpression(Expression e) {
        if (e instanceof Literal){
            return generateLiteral(Literal.class.cast(e));
        }
        // TODO
        return "";
    }

    private void generateDeclaration(Declaration d) {
        if (d instanceof Variable) {
            emit("var " + id(d) + " = 0;"); // TODO fix expression
        } else {
            Subroutine s = Subroutine.class.cast(d);
            StringBuilder builder = new StringBuilder();
            boolean first = true;
            for (Variable v: s.getParameters()) {
                if (!first) {
                    builder.append(",");
                }
                builder.append(id(v));
                first = false;
            }
            emit(String.format("function %s(%s) {", id(s), builder.toString()));
            generateBlock(s.getBody());
            emit("}");
        }
    }

    private String generateLiteral(Literal e) {
        if (BooleanLiteral.FALSE.equals(e)) {
            return "false";
        } else if (BooleanLiteral.TRUE.equals(e)) {
            return "true";
        } else if (e instanceof CharacterLiteral) {
            // TODO
        } else if (e instanceof StringLiteral) {
            // TODO
        } else if (e instanceof NumberLiteral) {
            return NumberLiteral.class.cast(e).getValue() + "";
        } else if (e instanceof WholeNumberLiteral){
            return WholeNumberLiteral.class.cast(e).getValue() + "";
        } else if (e instanceof NullLiteral){
            return "null";
        } else {
            // TODO log.error("internal.error.bad.literal");
        }
        return "NOT-DONE-YET";
    }
}
