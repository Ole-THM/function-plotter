package de.functionPlotter.ParsingEngine.Parser;


import de.functionPlotter.AbstractSyntaxTree.*;
import de.functionPlotter.ParsingEngine.Lexer.Lexer;
import de.functionPlotter.ParsingEngine.Lexer.Token;
import de.functionPlotter.ParsingEngine.Lexer.TokenType;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InfixParser implements ParserI {
    private List<Token> tokens;
    private int pos = 0;
    private final Lexer lexer;

    public InfixParser() { this.lexer = new Lexer(); }

    public ASTNodeI parse(String input) throws ParseException {
        this.tokens = this.lexer.tokenize(input);
        this.pos = 0;
        ASTNodeI result = new AST(parseExpression());
        if (this.tokens.get(pos).type() != TokenType.EOF) {
            throw new ParseException("Unerwartete Tokens am Ende: " + peek(), pos);
        }
        return result;
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
        ASTNodeI node = parsePrimary();
        if (match(TokenType.EXPONENT)) {
            TokenType op = previous().type();
            ASTNodeI right = parsePrimary();
            node = new BinaryOpNode(node, op, right);
        }
        return node;
    }
    private ASTNodeI parsePrimary() throws ParseException {
        if (match(TokenType.UNARYMINUS, TokenType.MINUS)) {
            // -x wird als UnaryOpNode gespeichert
            return new UnaryOpNode(parseFactor(), TokenType.UNARYMINUS);
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
            return parseFunctionCall();
        }
        throw new ParseException("Unerwartetes Token: " + peek(), this.pos);
    }

    private ASTNodeI parseFunctionCall() throws ParseException {
        String funcName = previous().text();
        expect(TokenType.OPENPARENTHESIS);
        ASTNodeI firstArg = parseExpression();
        ASTNodeI secondArg = null;
        if (match(TokenType.COMMA)) {
            secondArg = parseExpression();
        }
        List<ASTNodeI> args = new ArrayList<>(Arrays.asList(firstArg, secondArg));
        expect(TokenType.CLOSEPARENTHESIS);
        return new FunctionCallNode(funcName, args);
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
    private boolean check(TokenType type) { return peek().type() == type; }
    private Token previous() { return this.tokens.get(pos - 1); }
    private Token peek() { return this.tokens.get(pos); }
    private void expect(TokenType type) throws ParseException {
        if (!match(type)) throw new ParseException("Erwartet: " + type, this.pos);
    }

    @Override
    public boolean isValid(String input) {
        try {
            this.parse(input);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
