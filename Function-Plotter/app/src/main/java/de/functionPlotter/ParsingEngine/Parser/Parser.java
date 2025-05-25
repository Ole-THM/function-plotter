package de.functionPlotter.ParsingEngine.Parser;

import de.functionPlotter.AbstractSyntaxTree.ASTNodeI;

import java.text.ParseException;

public class Parser {

    private static final InfixParser infixParser = new InfixParser();
    private static final RPNParser RPNParser = new RPNParser();

    public static ASTNodeI parseInfix(String expression) throws ParseException {
        return infixParser.parse(expression);
    }

    public static ASTNodeI parseRPN(String expression) throws ParseException {
        return RPNParser.parse(expression);
    }
}
