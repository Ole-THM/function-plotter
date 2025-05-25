package de.functionPlotter.Utils;


import de.functionPlotter.AbstractSyntaxTree.ASTNodeI;
import de.functionPlotter.AbstractSyntaxTree.ValueNode;

import java.util.ArrayList;

public class Variables {

    private ArrayList<Variable> variables;

    public Variables() {
        this.variables = new ArrayList<>();
    }

    public void add(String name, ASTNodeI value) {
        this.variables.add(
                new Variable(
                        name,
                        value
                )
        );
    }

    public Variable getOrDefault(String name) {
        for (Variable variable : this.variables) {
            if (variable.getName().equals(name)) {
                return variable;
            }
        }
        return new Variable("default", new ValueNode(0)); // Variable not found return default variable
    }
}