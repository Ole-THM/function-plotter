package de.functionPlotter.Utils;


import de.functionPlotter.AbstractSyntaxTree.ASTNodeI;
import de.functionPlotter.AbstractSyntaxTree.ValueNode;

import java.util.ArrayList;

public class Variables {

    private ArrayList<Variable> variables;

    public Variables() {
        this.variables = new ArrayList<>();
    }

    public void add(Variable variable) {
        this.variables.add(variable);
    }

    public void set(String name, ASTNodeI node) {
        this.remove(name);
        this.add(new Variable(name, node));
    }

    public void remove(String name) {
        this.variables.removeIf(var -> var.name().equals(name));
    }

    public Variable getOrDefault(String name) {
        for (Variable variable : this.variables) {
            if (variable.name().equals(name)) {
                return variable;
            }
        }
        return new Variable("default", new ValueNode(0)); // Variable not found return default variable
    }
}