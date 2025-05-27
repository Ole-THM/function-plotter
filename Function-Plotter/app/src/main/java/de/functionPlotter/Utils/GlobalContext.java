package de.functionPlotter.Utils;

import de.functionPlotter.Plot.PlotUtils.XYRange;
import de.functionPlotter.Plot.PlotUtils.outputDimension;

public class GlobalContext {

    public static final Variables VARIABLES = new Variables();
    public static XYRange xyRange= new XYRange(-10,10,-5,5); // [xMin, xMax]
    public static StringBuilder outputString = new StringBuilder();
    public static outputDimension outputDimensions = new outputDimension(1000, 700); // [width, height]
}
