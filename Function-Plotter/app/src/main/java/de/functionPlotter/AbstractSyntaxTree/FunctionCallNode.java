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
            case "log" -> this.arguments.get(1) != null
                              ? logn(this.arguments.getFirst().evaluate(), this.arguments.get(1).evaluate())
                              : logn(Math.E, this.arguments.get(0).evaluate()); // defaults to natural log if no base is given
            default -> throw new UnsupportedOperationException("Unsupported function: " + functionName);
        };
    }

    private double logn(double base, double value) {
        return Math.log(value) / Math.log(base);
    }

    @Override
    public String toStringInfix() {
        return switch (functionName) {
            case "sin" -> "sin(" + this.arguments.getFirst().toStringInfix() + ")";
            case "cos" -> "cos(" + this.arguments.getFirst().toStringInfix() + ")";
            case "tan" -> "tan(" + this.arguments.getFirst().toStringInfix() + ")";
            case "sqrt" -> "sqrt(" + this.arguments.getFirst().toStringInfix() + ")";
            case "log" -> "log(" + (this.arguments.get(1) != null
                    ? this.arguments.getFirst().toStringInfix() + ", " + this.arguments.get(1).toStringInfix()
                    : String.format("%.4f", Math.E) + ", " + this.arguments.getFirst().toStringInfix()) + ")";
            default -> throw new UnsupportedOperationException("Unsupported function: " + functionName);
        };
    }

    @Override
    public String toStringRPN() {
        return switch (functionName) {
            case "sin" -> this.arguments.getFirst().toStringRPN() + " sin";
            case "cos" -> this.arguments.getFirst().toStringRPN() + " cos";
            case "tan" -> this.arguments.getFirst().toStringRPN() + " tan";
            case "sqrt" -> this.arguments.getFirst().toStringRPN() + " sqrt";
            case "log" -> (this.arguments.get(1) != null
                    ? this.arguments.getFirst().toStringRPN() + " " + this.arguments.get(1).toStringRPN()
                    : String.format("%.4f", Math.E) + ", " + this.arguments.getFirst().toStringRPN()) + " log";
            default -> throw new UnsupportedOperationException("Unsupported function: " + functionName);
        };
    }
}

