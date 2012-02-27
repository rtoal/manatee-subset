package edu.lmu.cs.xlg.manatee.generators;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import edu.lmu.cs.xlg.manatee.entities.ArrayConstructor;
import edu.lmu.cs.xlg.manatee.entities.AssignmentStatement;
import edu.lmu.cs.xlg.manatee.entities.BinaryExpression;
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
import edu.lmu.cs.xlg.manatee.entities.FunctionCall;
import edu.lmu.cs.xlg.manatee.entities.IdentifierExpression;
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
import edu.lmu.cs.xlg.manatee.entities.SubscriptExpression;
import edu.lmu.cs.xlg.manatee.entities.TimesLoop;
import edu.lmu.cs.xlg.manatee.entities.Type;
import edu.lmu.cs.xlg.manatee.entities.UnaryExpression;
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
     * Emits JavaScript code for the given Manatee statement.
     */
    private void generateStatement(Statement s) {
        if (s instanceof Declaration) {
            generateDeclaration(Declaration.class.cast(s));

        } else if (s instanceof DoNothingStatement) {
            emit(";");

        } else if (s instanceof AssignmentStatement) {
            AssignmentStatement a = AssignmentStatement.class.cast(s);
            String target = generateExpression(a.getTarget());
            String source = generateExpression(a.getSource());
            emit(String.format("%s = %s;", target, source));

        } else if (s instanceof ReadStatement) {
            // TODO
            emit("// READ STATEMENTS NOT YET HANDLED");

        } else if (s instanceof WriteStatement) {
            Expression e = WriteStatement.class.cast(s).getExpression();
            emit("console.log(" + generateExpression(e) + ");");

        } else if (s instanceof ExitStatement) {
            emit("break;");

        } else if (s instanceof ReturnStatement) {
            Expression e = ReturnStatement.class.cast(s).getExpression();
            emit("return" + (e == null ? "" : " " + generateExpression(e)) + ";");

        } else if (s instanceof CallStatement) {
            CallStatement call = CallStatement.class.cast(s);
            String p = id(call.getProcedure());
            List<String> arguments = new ArrayList<String>();
            for (Expression a: call.getArgs()) {
                arguments.add(generateExpression(a));
            }
            emit(String.format("%s(%s)", p, StringUtils.join(arguments, ", ")));

        } else if (s instanceof ModifiedStatement) {
            ModifiedStatement m = ModifiedStatement.class.cast(s);
            String key;
            switch (m.getModifier().getType()) {
            case IF: key = "if"; break;
            case WHILE: key = "while"; break;
            default: throw new RuntimeException("Internal error translating modifers");
            }
            String condition = generateExpression(m.getModifier().getCondition());
            emit(String.format("%s (%s) {", key, condition));
            indentLevel++;
            generateStatement(m.getStatement());
            indentLevel--;
            emit("}");

        } else if (s instanceof ConditionalStatement) {
            // TODO
            emit("// CONDITIONAL STATEMENTS NOT YET HANDLED");

        } else if (s instanceof PlainLoop) {
            emit("while (true) {");
            generateBlock(PlainLoop.class.cast(s).getBody());
            emit("}");

        } else if (s instanceof TimesLoop) {
            TimesLoop t = TimesLoop.class.cast(s);
            Variable v = new Variable("", Type.WHOLE_NUMBER);
            String count = generateExpression(t.getCount());
            emit(String.format("for (var %s = %s; %s > 0; %s--) {", id(v), count, id(v), id(v)));
            generateBlock(t.getBody());
            emit("}");

        } else if (s instanceof CollectionLoop) {
            // TODO
            emit("// COLLECTION LOOPS NOT YET HANDLED");

        } else if (s instanceof RangeLoop) {
            // TODO
            emit("// RANGE LOOPS NOT YET HANDLED");

        } else if (s instanceof WhileLoop) {
            WhileLoop w = WhileLoop.class.cast(s);
            emit("while (" + generateExpression(w.getCondition()) + ") {");
            generateBlock(w.getBody());
            emit("}");
        }
    }

    /**
     * Returns a JavaScript expression for the given Manatee expression.
     */
    private String generateExpression(Expression e) {
        if (e instanceof Literal) {
            return generateLiteral(Literal.class.cast(e));

        } else if (e instanceof IdentifierExpression) {
            return id(IdentifierExpression.class.cast(e).getReferent());

        } else if (e instanceof UnaryExpression) {
            return generateUnaryExpression(UnaryExpression.class.cast(e));

        } else if (e instanceof BinaryExpression) {
            return generateBinaryExpression(BinaryExpression.class.cast(e));

        } else if (e instanceof ArrayConstructor) {
            List<String> values = new ArrayList<String>();
            for (Expression element: ArrayConstructor.class.cast(e).getExpressions()) {
                values.add(generateExpression(element));
            }
            return String.format("[%s]", StringUtils.join(values, ", "));

        } else if (e instanceof SubscriptExpression) {
            SubscriptExpression s = SubscriptExpression.class.cast(e);
            String base = generateExpression(s.getBase());
            String index = generateExpression(s.getIndex());
            return String.format("%s[%s]", base, index);

        } else if (e instanceof FunctionCall) {
            FunctionCall call = FunctionCall.class.cast(e);
            String f = generateExpression(call.getFunction());
            List<String> arguments = new ArrayList<String>();
            for (Expression a: call.getArgs()) {
                arguments.add(generateExpression(a));
            }
            return String.format("%s(%s)", f, StringUtils.join(arguments, ", "));

        } else {
            throw new RuntimeException("Internal Operator: statement");
        }
    }

    /**
     * Emits JavaScript code for the given Manatee declaration.
     */
    private void generateDeclaration(Declaration d) {

        if (d instanceof Variable) {
            Variable v = Variable.class.cast(d);
            if (v.getInitializer() == null) {
                emit("var " + id(d) + ";");
            } else {
                emit("var " + id(d) + " = " + generateExpression(v.getInitializer()) + ";");
            }

        } else {
            Subroutine s = Subroutine.class.cast(d);
            List<String> parameters = new ArrayList<String>();
            for (Variable v: s.getParameters()) {
                parameters.add(id(v));
            }
            emit(String.format("function %s(%s) {", id(s), StringUtils.join(parameters, ", ")));
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
            return "TODO_CHAR_LITERAL";

        } else if (e instanceof StringLiteral) {
            return "TODO_STRING_LITERAL";

        } else if (e instanceof NumberLiteral) {
            return NumberLiteral.class.cast(e).getValue() + "";

        } else if (e instanceof WholeNumberLiteral) {
            return WholeNumberLiteral.class.cast(e).getValue() + "";

        } else if (e instanceof NullLiteral) {
            return "null";

        } else {
            throw new RuntimeException("Internal Operator in expression translation");
        }
    }

    /**
     * Returns JavaScript source for the given Manatee unary expression.
     */
    private String generateUnaryExpression(UnaryExpression e) {

        String operand = generateExpression(e.getOperand());
        if ("-".equals(e.getOp())) {
            return "(-(" + operand + "))";
        } else if ("not".equals(e.getOp())) {
            return "(!(" + operand + "))";
        } else if ("length".equals(e.getOp())) {
            return "((" + operand + ").length)";
        } else {
            throw new RuntimeException("InternalError in unary expression translation");
        }
    }

    /**
     * Returns JavaScript source for the given Manatee unary expression.
     */
    private String generateBinaryExpression(BinaryExpression e) {

        String op = e.getOp();
        String left = generateExpression(e.getLeft());
        String right = generateExpression(e.getRight());

        if (op.equals("+")) {
            if (e.getLeft().isArrayOrString() || e.getRight().isArray()) {
                if (e.getLeft().isArray()) {
                    if (e.getRight().isArray()) {
                        return left + ".concat(" + right + ")" ;
                    } else {
                        return left + ".push(" + right + ")" ;
                    }
                } else if (e.getRight().isArray()) {
                    return right + ".unshift(" + left + ")" ;
                } else {
                    return left + ".concat(" + right + ")" ;
                }
            }
        } else if (op.equals("*")) {
            if (e.getLeft().getType() == Type.STRING) {
                return "TODO_STRING_TIMES";
            }
        } else if (op.equals("and")) {
            op = "&&";
        } else if (op.equals("or")) {
            op = "||";
        } else if (op.equals("=")) {
            op = "===";
        } else if (op.equals("≠")) {
            op = "!==";
        } else if (op.matches("-|/|<<|>>|<|<=|>|>=")) {
            // Nothing here, just checking the operator is valid
        } else {
            throw new RuntimeException("InternalError in binary expression translation");
        }
        return String.format("(%s %s %s)", left, op, right);
    }
}
