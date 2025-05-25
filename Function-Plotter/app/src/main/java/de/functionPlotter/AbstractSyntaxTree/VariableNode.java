package de.functionPlotter.AbstractSyntaxTree;


import de.functionPlotter.Utils.GlobalContext;

public record VariableNode(String name) implements ASTNodeI {
    @Override
    public double evaluate() {
        return GlobalContext.VARIABLES.getOrDefault(this.name).evaluate();
    }

    @Override
    public String toStringInfix() {
        return GlobalContext.VARIABLES.getOrDefault(name).toStringInfix();
    }
}
