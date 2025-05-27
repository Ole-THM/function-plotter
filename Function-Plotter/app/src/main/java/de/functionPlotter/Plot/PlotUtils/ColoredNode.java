package de.functionPlotter.Plot.PlotUtils;

import de.functionPlotter.AbstractSyntaxTree.ASTNodeI;

public record ColoredNode(ASTNodeI ast, RGB color) {}