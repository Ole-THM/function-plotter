package de.functionPlotter.Plot;

import de.functionPlotter.Utils.GlobalContext;

public class BaseCoordinateSystem {
    public static void genBase(double xMin, double xMax, double yMin, double yMax) {
        System.out.println("Drawing grid...");
        genGrid(xMin, xMax, yMin, yMax);
        genYAxis(xMin, xMax);
        genXAxis(yMin, yMax);
    }

    private static void genXAxis(double yMin, double yMax) {
        System.out.println("Drawing X-axis from " + yMin + " to " + yMax);
        if (yMax < 0 || yMin > 0) return; // No X-axis to draw if yMax is negative
        int width = GlobalContext.outputDimensions[0];
        int height = GlobalContext.outputDimensions[1];
        int yAxisPosition = (int) ((-yMin / (yMax - yMin)) * height);
        GlobalContext.outputString.append("<line x1=\"0\" y1=\"")
                .append(yAxisPosition)
                .append("\" x2=\"")
                .append(width)
                .append("\" y2=\"")
                .append(yAxisPosition)
                .append("\" style=\"stroke:rgb(0,0,0);stroke-width:2\"/>\n");
    }

    private static void genYAxis(double xMin, double xMax) {
        System.out.println("Drawing Y-axis from " + xMin + " to " + xMax);
        if (xMax < 0 || xMin > 0) return; // No Y-axis to draw if xMax is negative
        int width = GlobalContext.outputDimensions[0];
        int height = GlobalContext.outputDimensions[1];
        int xAxisPosition = (int) ((-xMin / (xMax - xMin)) * width);
        GlobalContext.outputString.append("<line x1=\"")
                .append(xAxisPosition)
                .append("\" y1=\"0\" x2=\"")
                .append(xAxisPosition)
                .append("\" y2=\"")
                .append(height)
                .append("\" style=\"stroke:rgb(0,0,0);stroke-width:3\"/>\n");
    }

    private static void genGrid(double xMin, double xMax, double yMin, double yMax) {
        System.out.println("Drawing grid lines...");
        int width = GlobalContext.outputDimensions[0];
        int height = GlobalContext.outputDimensions[1];

        // Vertical grid lines
        for (double x = xMin; x <= xMax; x++) {
            int xPos = (int) ((x - xMin) / (xMax - xMin) * width);
            GlobalContext.outputString.append("<line x1=\"")
                    .append(xPos)
                    .append("\" y1=\"0\" x2=\"")
                    .append(xPos)
                    .append("\" y2=\"")
                    .append(height)
                    .append("\" style=\"stroke:rgb(200,200,200);stroke-width:2\"/>\n");
        }

        // Horizontal grid lines
        for (double y = yMin; y <= yMax; y++) {
            int yPos = (int) ((y - yMin) / (yMax - yMin) * height);
            GlobalContext.outputString.append("<line x1=\"0\" y1=\"")
                    .append(yPos)
                    .append("\" x2=\"")
                    .append(width)
                    .append("\" y2=\"")
                    .append(yPos)
                    .append("\" style=\"stroke:rgb(200,200,200);stroke-width:1\"/>\n");
        }
    }
}