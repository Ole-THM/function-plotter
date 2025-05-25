package de.functionPlotter.AbstractSyntaxTree;

public sealed interface ASTNodeI permits AST, BinaryOpNode, FunctionCallNode, UnaryOpNode, ValueNode, VariableNode {
    double evaluate();
    String toStringInfix();
}