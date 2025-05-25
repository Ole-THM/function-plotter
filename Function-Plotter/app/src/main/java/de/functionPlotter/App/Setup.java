package de.functionPlotter.App;

import de.functionPlotter.AbstractSyntaxTree.ValueNode;
import de.functionPlotter.Utils.GlobalContext;

public class Setup {

    public static void setUp() {
        setUpConstants();
    }

    private static void setUpConstants() {
        GlobalContext.VARIABLES.add("pi", new ValueNode(Math.PI));
        GlobalContext.VARIABLES.add("e", new ValueNode(Math.E));
    }
}
