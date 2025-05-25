package de.functionPlotter.ParsingEngine.Parser;

import de.functionPlotter.AbstractSyntaxTree.ASTNodeI;

import java.text.ParseException;

public class Parser {

    private static final InfixParser infixParser = new InfixParser();
    private static final RPNParser RPNParser = new RPNParser();

    public static ASTNodeI parse(String expression) throws ParseException {
        if (infixParser.isValid(expression)) {
            return infixParser.parse(expression);
        } else if (RPNParser.isValid(expression)) {
            return RPNParser.parse(expression);
        } else {
            throw new ParseException("Invalid expression: " + expression, 0);
        }
    }

    public static ASTNodeI parseInfix(String expression) throws ParseException {
        return infixParser.parse(expression);
    }

    public static ASTNodeI parseRPN(String expression) throws ParseException {
        return RPNParser.parse(expression);
    }
}
