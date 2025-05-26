package de.functionPlotter.AbstractSyntaxTree;


import de.functionPlotter.ParsingEngine.Lexer.TokenType;

public record UnaryOpNode(ASTNodeI node, TokenType op) implements ASTNodeI {
    @Override
    public double evaluate() {
        return switch (op) {
            case PLUS -> this.node.evaluate(); // Unary plus, no change
            case UNARYMINUS -> -this.node.evaluate(); // Unary minus, negate the astNode
            default -> throw new UnsupportedOperationException("Unsupported operation: " + op);
        };
    }

    @Override
    public String toStringInfix() {
        return switch (op) {
            case PLUS -> this.node.toStringInfix(); // Unary plus, no change
            case UNARYMINUS -> "¯" + this.node.toStringInfix(); // Unary minus, negate the astNode
            default -> throw new UnsupportedOperationException("Unsupported operation: " + op);
        };

    }

    @Override
    public String toStringRPN() {
        return switch (op) {
            case PLUS -> this.node.toStringInfix(); // Unary plus, no change
            case UNARYMINUS -> this.node.toStringInfix() + "¯"; // Unary minus, negate the astNode
            default -> throw new UnsupportedOperationException("Unsupported operation: " + op);
        };
    }
}