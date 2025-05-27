package de.functionPlotter.Plot;

import de.functionPlotter.Utils.GlobalContext;
/*
* X Axis scaling idea:
* Example logarithmic scaling:
* scaling of the X Axis means taking the the base x values from the given xyRange and what ever value log(x) returns,
* is the new x value for that point.
* This means that the x values are not linear anymore, but logarithmic.
* Example:
* x_1 - x_10 = 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
* log(x_1) - log(x_10) = 0, 0.301, 0.477, 0.602, 0.699, 0.778, 0.845, 0.903, 0.954, 1
* After applying the logarithmic scaling, plotting the log(x) function should result in a straight line (visually)
 */
public class BaseCoordinateSystem {

    private static int axisXPos = -1; // position of the X-axis in the SVG output
    private static int axisYPos = -1; // position of the Y-axis in the SVG output

    public static void genBase() {
        System.out.println("Drawing grid...");
        double xMin = GlobalContext.xyRange.xMin();
        double xMax = GlobalContext.xyRange.xMax();
        double yMin = GlobalContext.xyRange.yMin();
        double yMax = GlobalContext.xyRange.yMax();
        axisXPos = (int) ((-xMin / (xMax - xMin)) * GlobalContext.outputDimensions.width());
        axisYPos = (int) ((-yMin / (yMax - yMin)) * GlobalContext.outputDimensions.height());
        genGrid(xMin, xMax, yMin, yMax);
        genYAxis(xMin, xMax);
        genXAxis(yMin, yMax);
    }

    private static void genXAxis(double yMin, double yMax) {
        System.out.println("Drawing X-axis from " + yMin + " to " + yMax);
        if (yMax < 0 || yMin > 0) return; // return if the axis is not in the range
        int width = GlobalContext.outputDimensions.width();
        GlobalContext.outputString.append("<line x1=\"0\" y1=\"")
                .append(axisYPos)
                .append("\" x2=\"")
                .append(width)
                .append("\" y2=\"")
                .append(axisYPos)
                .append("\" style=\"stroke:rgb(0,0,0);stroke-width:2\"/>\n");
    }

    private static void genYAxis(double xMin, double xMax) {
        System.out.println("Drawing Y-axis from " + xMin + " to " + xMax);
        if (xMax < 0 || xMin > 0) return;
        int height = GlobalContext.outputDimensions.height();
        GlobalContext.outputString.append("<line x1=\"")
                .append(axisXPos)
                .append("\" y1=\"0\" x2=\"")
                .append(axisXPos)
                .append("\" y2=\"")
                .append(height)
                .append("\" style=\"stroke:rgb(0,0,0);stroke-width:3\"/>\n");
    }

    private static void genGrid(double xMin, double xMax, double yMin, double yMax) {
        System.out.println("Drawing grid lines...");
        int width = GlobalContext.outputDimensions.width();
        int height = GlobalContext.outputDimensions.height();

        double xRange = GlobalContext.xyRange.xRange();
        double yRange = GlobalContext.xyRange.yRange();

        int targetLines = 20; // Number of grid lines to be drawn ideally

        double xStep = calculateGridStep(xRange / targetLines);
        double yStep = calculateGridStep(yRange / targetLines);

        // vertical lines (x)
        double xStart = xMin / xStep * xStep;
        for (double x = xStart; x <= xMax; x += xStep) {
            int xPos = (int) ((x - xMin) / xRange * width);
            GlobalContext.outputString.append("<line x1=\"")
                    .append(xPos)
                    .append("\" y1=\"0\" x2=\"")
                    .append(xPos)
                    .append("\" y2=\"")
                    .append(height)
                    .append("\" style=\"stroke:rgb(200,200,200);stroke-width:2\"/>\n");
            drawVerticalLineGridLabel(x, xPos);
        }

        // horizontal lines (y)
        double yStart = yMin / yStep * yStep;
        System.out.println(yStart);
        for (double y = yStart; y <= yMax; y += yStep) {
            int yPos = height - (int) ((y - yMin) / yRange * height);
            GlobalContext.outputString.append("<line x1=\"0\" y1=\"")
                    .append(yPos)
                    .append("\" x2=\"")
                    .append(width)
                    .append("\" y2=\"")
                    .append(yPos)
                    .append("\" style=\"stroke:rgb(200,200,200);stroke-width:2\"/>\n");
            drawHorizontalLineGridLabel(y, yPos);
        }
    }

    private static double calculateGridStep(double num) throws IllegalArgumentException {
        if (num <= 0) {
            throw new IllegalArgumentException("Grid step must be greater than zero, got: " + num);
        }
        double exponent = Math.floor(Math.log10(num));
        double base = Math.pow(10, exponent);
        double mantissa = num / base; // Normalizes to [1, 10)

        double factor;
        if (mantissa >= 5) {
            factor = 5;
        } else if (mantissa >= 2) {
            factor = 2;
        } else {
            factor = 1;
        }

        return factor * base;
    }

    private static void drawVerticalLineGridLabel(double value, int xPos) {
        String label = String.valueOf(value)
                .replaceAll("\\.0+$", "")
                .replaceAll("(\\.\\d*?)0+$", "$1")
                .replaceAll("\\.$", ""); // Remove trailing zeros
        int yPos = axisYPos + 15; // Position below the X-axis
        GlobalContext.outputString.append("<text x=\"")
                .append(xPos - 10) // Adjust x position for better visibility
                .append("\" y=\"")
                .append(yPos)
                .append("\" font-size=\"12\" text-anchor=\"middle\">")
                .append(label)
                .append("</text>\n");
    }

    private static void drawHorizontalLineGridLabel(double value, int yPos) {
        String label = String.valueOf(value)
                .replaceAll("\\.0+$", "")
                .replaceAll("(\\.\\d*?)0+$", "$1")
                .replaceAll("\\.$", ""); // Remove trailing zeros
        int xPos = axisXPos + 10; // Position to the right of the Y-axis
        GlobalContext.outputString.append("<text x=\"")
                .append(xPos)
                .append("\" y=\"")
                .append(yPos + 15) // Adjust y position for better visibility
                .append("\" font-size=\"12\" text-anchor=\"start\">")
                .append(label)
                .append("</text>\n");
    }
}