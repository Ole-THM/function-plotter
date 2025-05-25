package de.functionPlotter.ParsingEngine.Parser;


import de.functionPlotter.AbstractSyntaxTree.*;
import de.functionPlotter.ParsingEngine.Lexer.Lexer;
import de.functionPlotter.ParsingEngine.Lexer.Token;

import java.text.ParseException;
import java.util.List;
import java.util.Stack;

public class RPNParser implements ParserI {
    private final Stack<ASTNodeI> stack = new Stack<>();
    private final Lexer lexer;

    public RPNParser() { this.lexer = new Lexer(); }

    public ASTNodeI parse(String input) throws ParseException {
        List<Token> tokens = this.lexer.tokenize(input);
        for (Token token : tokens) {
            switch (token.type()) {
                case NUMBER -> {
                    this.stack.push(
                            new ValueNode(Double.parseDouble(token.text()))
                    );
                }
                case IDENTIFIER -> {
                    this.stack.push(
                            new VariableNode(token.text())
                    );
                }
                case FUNCTION -> {
                    this.stack.push(
                            new FunctionCallNode(token.text(), List.of(this.stack.pop()))
                    );
                }
                case MINUS, PLUS, MULTIPLY, DIVIDE, EXPONENT -> {
                    ASTNodeI right = this.stack.pop();
                    ASTNodeI left = this.stack.pop();
                    this.stack.push(
                            new BinaryOpNode(left, token.type(), right)
                    );
                }
                case EOF -> {
                    break;
                }
                default -> throw new ParseException("Unexpected Token: " + token.text(), 0);
            }
        }
        return new AST().setRoot(this.stack.pop());
    }

    @Override
    public boolean isValid(String input) throws ParseException {
        return false;
    }


}