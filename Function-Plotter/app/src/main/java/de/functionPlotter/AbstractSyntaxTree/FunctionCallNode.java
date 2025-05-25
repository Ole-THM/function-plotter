package de.functionPlotter.AbstractSyntaxTree;

import java.util.List;

public record FunctionCallNode(String functionName, List<ASTNodeI> arguments) implements ASTNodeI {
    @Override
    public double evaluate() {
        return switch (functionName) {
            case "sin" -> Math.sin(this.arguments.getFirst().evaluate());
            case "cos" -> Math.cos(this.arguments.getFirst().evaluate());
            case "tan" -> Math.tan(this.arguments.getFirst().evaluate());
            case "sqrt" -> Math.sqrt(this.arguments.getFirst().evaluate());
            case "log" -> Math.log(this.arguments.getFirst().evaluate());
            default -> throw new UnsupportedOperationException("Unsupported function: " + functionName);
        };
    }

    @Override
    public String toStringInfix() {
        return switch (functionName) {
            case "sin" -> "sin(" + this.arguments.getFirst().toStringInfix() + ")";
            case "cos" -> "cos(" + this.arguments.getFirst().toStringInfix() + ")";
            case "tan" -> "tan(" + this.arguments.getFirst().toStringInfix() + ")";
            case "sqrt" -> "sqrt(" + this.arguments.getFirst().toStringInfix() + ")";
            case "log" -> "log(" + this.arguments.getFirst().toStringInfix() + ")";
            default -> throw new UnsupportedOperationException("Unsupported function: " + functionName);
        };

    }
}

