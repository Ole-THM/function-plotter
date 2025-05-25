package de.functionPlotter.AbstractSyntaxTree;


import de.functionPlotter.ParsingEngine.Lexer.TokenType;

public record BinaryOpNode(ASTNodeI left, TokenType op, ASTNodeI right) implements ASTNodeI {
    @Override
    public double evaluate() {
        return switch (op) {
            case PLUS -> this.left.evaluate() + this.right.evaluate();
            case MINUS -> this.left.evaluate() - this.right.evaluate();
            case MULTIPLY -> this.left.evaluate() * this.right.evaluate();
            case DIVIDE -> this.left.evaluate() / this.right.evaluate();
            case EXPONENT -> Math.pow(this.left.evaluate(), this.right.evaluate());
            default -> throw new UnsupportedOperationException("Unsupported operation: " + op);
        };
    }

    @Override
    public String toStringInfix() {
        String leftStr = left instanceof BinaryOpNode lNode && precedence(lNode.op()) < precedence(this.op)
                ? "(" + left.toStringInfix() + ")"
                : left.toStringInfix();
        String rightStr = right instanceof BinaryOpNode rNode && precedence(rNode.op()) <= precedence(this.op)
                ? "(" + right.toStringInfix() + ")"
                : right.toStringInfix();
        return leftStr + " " + opToString(op) + " " + rightStr;
    }

    private String opToString(TokenType op) {
        return switch (op) {
            case PLUS -> "+";
            case MINUS -> "-";
            case MULTIPLY -> "*";
            case DIVIDE -> "/";
            case EXPONENT -> "^";
            default -> "?";
        };
    }

    private int precedence(TokenType op) {
        return switch (op) {
            case PLUS, MINUS -> 1;
            case MULTIPLY, DIVIDE -> 2;
            case EXPONENT -> 3;
            default -> 0;
        };
    }
}