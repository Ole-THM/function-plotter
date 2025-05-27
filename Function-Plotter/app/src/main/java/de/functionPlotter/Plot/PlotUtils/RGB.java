package de.functionPlotter.Plot.PlotUtils;

public record RGB(int red, int green, int blue) {

    public RGB {
        if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
            throw new IllegalArgumentException("RGB values must be in the range [0, 255]");
        }
    }

    public String toHex() {
        return String.format("#%02X%02X%02X", red, green, blue);
    }
}
