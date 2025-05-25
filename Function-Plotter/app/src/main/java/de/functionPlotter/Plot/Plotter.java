package de.functionPlotter.Plot;

import de.functionPlotter.AbstractSyntaxTree.ASTNodeI;
import de.functionPlotter.AbstractSyntaxTree.ValueNode;
import de.functionPlotter.Utils.GlobalContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Plotter {

    public static void plot(ASTNodeI...ast) {
        GlobalContext.outputString = new StringBuilder();
        GlobalContext.outputString.append("<svg width=\"").append(GlobalContext.outputDimensions[0]).append("\" height=\"").append(GlobalContext.outputDimensions[1]).append("\" xmlns=\"http://www.w3.org/2000/svg\">\n");

        BaseCoordinateSystem.genBase(-10,10,-10,10);
        for (ASTNodeI tree : ast) {
            System.out.println("Plotting function: " + tree.toStringInfix());
            plotFunction(tree);
        }

        GlobalContext.outputString.append("</svg>\n");
        saveSvgToFile(GlobalContext.outputString.toString());
    }

    private static void plotFunction(ASTNodeI ast) {
        for (double x = GlobalContext.xyRange.xMin(); x <= GlobalContext.xyRange.xMax(); x += getStepSize()) {
            GlobalContext.VARIABLES.set("x", new ValueNode(x));
            double y = ast.evaluate();
            int xPos = (int) ((x - GlobalContext.xyRange.xMin()) / (GlobalContext.xyRange.xMax() - GlobalContext.xyRange.xMin()) * GlobalContext.outputDimensions[0]);
            int yPos = (int) ((y - GlobalContext.xyRange.yMin()) / (GlobalContext.xyRange.yMax() - GlobalContext.xyRange.yMin()) * GlobalContext.outputDimensions[1]);
            GlobalContext.outputString.append("<circle cx=\"").append(xPos).append("\" cy=\"").append(GlobalContext.outputDimensions[1] - yPos).append("\" r=\"2\" fill=\"black\"/>\n");
        }
        double value = ast.evaluate();
    }

    private static double getStepSize() {
        // Adjust step size based on the range and resolution
        double range = GlobalContext.xyRange.xMax() - GlobalContext.xyRange.xMin();
        return range / GlobalContext.outputDimensions[0]; // Step size based on width
    }

    private static void saveSvgToFile(String svgContent) {
        Path filePath = Paths.get("src", "main", "resources", "output.svg");
        try {
            Files.writeString(filePath, svgContent);
            System.out.println("SVG saved to " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to save SVG to file: " + e.getMessage());
        }
    }

}