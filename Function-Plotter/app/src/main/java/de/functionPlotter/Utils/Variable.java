package de.functionPlotter.Utils;


import de.functionPlotter.AbstractSyntaxTree.ASTNodeI;

public record Variable(String name, ASTNodeI value) {

    public String getName() {
        return name;
    }

    public ASTNodeI getValue() {
        return value;
    }

    public double evaluate() {
        if (value == null) {
            throw new IllegalStateException("Variable value is not set.");
        }
        return value.evaluate();
    }

    @Override
    public String toString() {
        return "Variable{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    public String toStringInfix() {
        return this.value.toStringInfix();
    }

}
