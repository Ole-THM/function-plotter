package de.functionPlotter.AbstractSyntaxTree;


public class AST implements ASTNodeI {

    private ASTNodeI root;

    public AST() {}

    public void setRoot(ASTNodeI root) {
        this.root = root;
    }

    public ASTNodeI getRoot() {
        return this.root;
    }

    @Override
    public double evaluate() {
        if (this.root == null) {
            throw new IllegalStateException("AST root is not set.");
        }
        return this.root.evaluate();
    }

    public String toStringInfix() {
        if (this.root == null) {
            throw new IllegalStateException("AST root is not set.");
        }
        return this.root.toStringInfix();
    }
}