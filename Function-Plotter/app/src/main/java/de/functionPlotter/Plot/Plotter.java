package de.functionPlotter.Plot;

import de.functionPlotter.AbstractSyntaxTree.ValueNode;
import de.functionPlotter.Plot.PlotUtils.ColoredNode;
import de.functionPlotter.Utils.GlobalContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Plotter {

    public static void plot(ColoredNode[] coloredNodes) {
        GlobalContext.outputString = new StringBuilder();
        int width = GlobalContext.outputDimensions.width();
        int height = GlobalContext.outputDimensions.height();

        // Set the viewBox and preserveAspectRatio attributes
        GlobalContext.outputString.append("<svg width=\"").append(width).append("\" height=\"").append(height)
                .append("\" viewBox=\"0 0 ").append(width).append(" ").append(height)
                .append("\" preserveAspectRatio=\"xMidYMid meet\" xmlns=\"http://www.w3.org/2000/svg\">\n");
        BaseCoordinateSystem.genBase();
        for (ColoredNode node : coloredNodes) {
            System.out.println("Plotting function: " + node.ast().toStringInfix());
            plotFunction(node);
        }

        GlobalContext.outputString.append("</svg>\n");
        saveSvgToFile(GlobalContext.outputString.toString());
    }

private static void plotFunction(ColoredNode coloredNode) {
    Double prevX = null, prevY = null;
    for (double x = GlobalContext.xyRange.xMin(); x <= GlobalContext.xyRange.xMax(); x += getStepSize()) {
        GlobalContext.VARIABLES.set("x", new ValueNode(x));
        double y = coloredNode.ast().evaluate();
        int xPos = (int) ((x - GlobalContext.xyRange.xMin()) / (GlobalContext.xyRange.xMax() - GlobalContext.xyRange.xMin()) * GlobalContext.outputDimensions.width());
        int yPos = (int) ((y - GlobalContext.xyRange.yMin()) / (GlobalContext.xyRange.yMax() - GlobalContext.xyRange.yMin()) * GlobalContext.outputDimensions.height());
        yPos = GlobalContext.outputDimensions.height() - yPos;
        if (prevX != null && prevY != null) {
            GlobalContext.outputString.append("<line x1=\"").append(prevX.intValue())
                .append("\" y1=\"").append(prevY.intValue())
                .append("\" x2=\"").append(xPos)
                .append("\" y2=\"").append(yPos)
                .append("\" stroke=\"rgb(")
                .append(coloredNode.color().red()).append(",")
                .append(coloredNode.color().green()).append(",")
                .append(coloredNode.color().blue())
                .append(")\" stroke-width=\"2\"/>\n");
        }
        prevX = (double) xPos;
        prevY = (double) yPos;
    }
}

    private static double getStepSize() {
        // Adjust step size based on the range and resolution
        double range = GlobalContext.xyRange.xMax() - GlobalContext.xyRange.xMin();
        return range / GlobalContext.outputDimensions.width(); // Step size based on width
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