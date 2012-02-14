package edu.lmu.cs.xlg.manatee.entities;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import edu.lmu.cs.xlg.util.Log;

/**
 * A Manatee entity.
 *
 * <p>The front end of the compiler produces an intermediate representation in the form of a graph.
 * Each entity in the graph has some syntactic and semantic content.  The syntactic content is
 * filled in by the entity's constructor, and the semantic content is filled in by its
 * <code>analyze</code> method.  Entities also have an <code>optimize</code> method for
 * simple local improvements.</p>
 *
 * <p>The entities are naturally grouped into a hierarchy of classes:</p>
 *
 * <pre>
 * Entity
 *     Block (statements)
 *         Script
 *     Declaration (name)
 *         Variable (typeName, initializer, constant)
 *         Procedure (parameters, body)
 *         Function (returnTypeName, parameters, body)
 *     Type
 *         ArrayType (baseType*)
 *     Statement
 *         DoNothingStatement
 *         AssignmentStatement (target, source)
 *         ReadStatement (expression)
 *         WriteStatement (expression)
 *         ExitStatement
 *         ReturnStatement (expression)
 *         ModifiedStatement (modifier, condition)
 *         WhileStatement (condition, body)
 *         CallStatement (procedureName, args)
 *         ConditionalStatement (arms, elsePart)
 *         PlainLoop (body)
 *         TimesLoop (count, body)
 *         CollectionLoop (iteratorName, collection, body)
 *         RangeLoop (iteratorName, low, high, step, body)
 *     Expression
 *         Literal (lexeme)
 *             BooleanLiteral
 *             CharacterLiteral
 *             NumberLiteral
 *             WholeNumberLiteral
 *             StringLiteral
 *             NullLiteral
 *         VariableReference (name)
 *         UnaryExpression (operator, operand)
 *         BinaryExpression (operator, left, right)
 *         ArrayConstructor (expressions)
 *         SubscriptExpression (base, subscript)
 *         FunctionCall (function, args)
 * </pre>
 */
public abstract class Entity {

    /**
     * Writes a simple, indented, syntax tree rooted at the given entity to the given print
     * writer.  Each level is indented two spaces.
     */
    public final void printSyntaxTree(String indent, String prefix, PrintWriter out) {

        // Prepare the line to be written
        String className = getClass().getName();
        String line = indent + prefix + className.substring(className.lastIndexOf('.') + 1);

        // Process the fields, adding plain attributes to the line, but storing all the entity
        // children in a linked hash map to be processed after the line is written.  We use a
        // linked hash map because the order of output is important.
        Map<String, Entity> children = new LinkedHashMap<String, Entity>();
        for (Map.Entry<String, Object> entry: attributes().entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            } else if (value instanceof Entity) {
                children.put(name, Entity.class.cast(value));
            } else if (value instanceof Iterable<?>) {
                try {
                    int index = 0;
                    for (Object child : (Iterable<?>) value) {
                        children.put(name + "[" + (index++) + "]", (Entity) child);
                    }
                } catch (ClassCastException cce) {
                    // Special case for non-entity collections
                    line += " " + name + "=\"" + value + "\"";
                }
            } else {
                // Simple attribute, attach description to node name
                line += " " + name + "=\"" + value + "\"";
            }
        }
        out.println(line);

        // Now we can go through all the entity children that were saved up earlier
        for (Map.Entry<String, Entity> child: children.entrySet()) {
            child.getValue().printSyntaxTree(indent + "  ", child.getKey() + ": ", out);
        }
    }

    /**
     * Traverses the semantic graph starting at this entity, applying visitor v to each entity.
     */
    public void traverse(Visitor v, Set<Entity> visited) {

        // The graph may have cycles, so skip this entity if we have seen it before.  If we
        // haven't, mark it seen.
        if (visited.contains(this)) {
            return;
        }
        visited.add(this);

        v.onEntry(this);
        for (Map.Entry<String, Object> entry: attributes().entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Entity) {
                Entity.class.cast(value).traverse(v, visited);
            } else if (value instanceof Iterable<?>) {
                for (Object child : (Iterable<?>) value) {
                    Entity.class.cast(child).traverse(v, visited);
                }
            }
        }
        v.onExit(this);
    }

    public static interface Visitor {
        void onEntry(Entity e);
        void onExit(Entity e);
    }

    public final void printEntities(PrintWriter writer) {
        writer.println("Semantic dump not implemented");
    }

    /**
     * Returns a map of name-value pairs for the given entity's fields and their values.  The
     * set of fields computed here are the non-static declared fields of its class, together with
     * the relevant fields of its ancestor classes, up to but not including the class Entity
     * itself.
     */
    private Map<String, Object> attributes() {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        for (Class<?> c = getClass(); c != Entity.class; c = c.getSuperclass()) {
            for (Field field: c.getDeclaredFields()) {
                if ((field.getModifiers() & Modifier.STATIC) == 0) {
                    try {
                        field.setAccessible(true);
                        result.put(field.getName(), field.get(this));
                    } catch (IllegalAccessException cannotHappen) {
                    }
                }
            }
        }
        return result;
    }

    /**
     * Performs semantic analysis on this entity, and (necessarily) on its descendants.
     * @param log
     *            the destination for info and error messages
     * @param table
     *            the current symbol table in which we are to analyze this entity
     * @param owner
     *            the innermost enclosing procedure or function, used to analyze return statements
     * @param inLoop
     *            whether this analysis is taking place within a loop, needed in order to analyze
     *            exit statements.
     */
    public abstract void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop);
}
