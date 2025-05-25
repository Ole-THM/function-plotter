package de.functionPlotter.Utils;

import de.functionPlotter.Plot.XYRange;

public class GlobalContext {

    public static final Variables VARIABLES = new Variables();
    public static XYRange xyRange= new XYRange(-10,10,-10,10); // [xMin, xMax]
    public static StringBuilder outputString = new StringBuilder();
    public static int[] outputDimensions = new int[]{1280, 720}; // [width, height]
}
