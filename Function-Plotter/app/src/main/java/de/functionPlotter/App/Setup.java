package de.functionPlotter.App;

import de.functionPlotter.AbstractSyntaxTree.ValueNode;
import de.functionPlotter.Utils.GlobalContext;
import de.functionPlotter.Utils.Variable;

public class Setup {

    public static void setUp() {
        setUpConstants();
        setUpVariables();
    }

    private static void setUpConstants() {
        GlobalContext.VARIABLES.add(
                new Variable(
                        "pi",
                        new ValueNode(Math.PI)
                )
        );
        GlobalContext.VARIABLES.add(
                new Variable(
                        "e",
                        new ValueNode(Math.E)
                )
        );
    }

    private static void setUpVariables() {
        GlobalContext.VARIABLES.add(
                new Variable(
                        "x",
                        new ValueNode(0)
                )
        ); // Default astNode for x
    }
}
