package de.functionPlotter.ParsingEngine.Parser;


import de.functionPlotter.AbstractSyntaxTree.*;
import de.functionPlotter.ParsingEngine.Lexer.Lexer;
import de.functionPlotter.ParsingEngine.Lexer.Token;
import de.functionPlotter.ParsingEngine.Lexer.TokenType;

import java.text.ParseException;
import java.util.List;

public class InfixParser implements ParserI {
    private List<Token> tokens;
    private int pos = 0;
    private final Lexer lexer;

    public InfixParser() { this.lexer = new Lexer(); }

    public ASTNodeI parse(String input) throws ParseException {
        this.tokens = this.lexer.tokenize(input);
        this.pos = 0;
        return parseExpression();
    }

    private ASTNodeI parseExpression() throws ParseException {
        ASTNodeI node = parseTerm();
        while (match(TokenType.PLUS, TokenType.MINUS)) {
            TokenType op = previous().type();
            ASTNodeI right = parseTerm();
            node = new BinaryOpNode(node, op, right);
        }
        return node;
    }

    private ASTNodeI parseTerm() throws ParseException {
        ASTNodeI node = parseFactor();
        while (match(TokenType.MULTIPLY, TokenType.DIVIDE)) {
            TokenType op = previous().type();
            ASTNodeI right = parseFactor();
            node = new BinaryOpNode(node, op, right);
        }
        return node;
    }

    private ASTNodeI parseFactor() throws ParseException {
        ASTNodeI node = parseExponent();
        if (match(TokenType.EXPONENT)) {
            TokenType op = previous().type();
            ASTNodeI right = parseExponent();
            node = new BinaryOpNode(node, op, right);
        }
        return node;
    }
    private ASTNodeI parseExponent() throws ParseException {
        if (match(TokenType.MINUS)) {
            // -x wird als UnaryOpNode gespeichert
            return new UnaryOpNode(parseFactor(), TokenType.MINUS);
        }
        if (match(TokenType.NUMBER)) {
            return new ValueNode(Double.parseDouble(previous().text()));
        }
        if (match(TokenType.IDENTIFIER)) {
            return new VariableNode(previous().text());
        }
        if (match(TokenType.OPENPARENTHESIS)) {
            ASTNodeI expr = parseExpression();
            expect(TokenType.CLOSEPARENTHESIS);
            return expr;
        }
        if (match(TokenType.FUNCTION)) {
            String funcName = previous().text();
            expect(TokenType.OPENPARENTHESIS);
            ASTNodeI arg = parseExpression();
            expect(TokenType.CLOSEPARENTHESIS);
            return new FunctionCallNode(funcName, List.of(arg));
        }
        throw new ParseException("Unerwartetes Token: " + peek(), this.pos);
    }

    // Hilfsmethoden
    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                pos++;
                return true;
            }
        }
        return false;
    }
    private boolean check(TokenType type) {
        return peek().type() == type;
    }
    private Token previous() { return tokens.get(pos - 1); }
    private Token peek() { return tokens.get(pos); }
    private void expect(TokenType type) throws ParseException {
        if (!match(type)) throw new ParseException("Erwartet: " + type, this.pos);
    }

    @Override
    public boolean isValid(String input) throws ParseException {
        return false;
    }
}
