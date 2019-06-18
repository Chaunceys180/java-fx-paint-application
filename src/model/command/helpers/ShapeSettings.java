package model.command.helpers;

import javafx.scene.paint.Paint;

/**
 * This class is a helper class that stores shape settings for the shape they want to draw
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class ShapeSettings {
    private double strokeSize;
    private Paint strokeColor;
    private boolean isFilled;
    private Paint fillColor;

    /**
     * This function is the constructor for the ShapeSettings class
     * @param strokeSize is the size that the user wants the stroke of the shape to be
     * @param strokeColor is the color if the stroke that they want the shape to be
     * @param isFilled is true|false depending on if they want the shape to be filled
     * @param fillColor is the color of the fill for the shape
     */
    public ShapeSettings(double strokeSize, Paint strokeColor, boolean isFilled, Paint fillColor) {
        this.strokeSize = strokeSize;
        this.strokeColor = strokeColor;
        this.isFilled = isFilled;
        this.fillColor = fillColor;
    }

    /**
     * returns the size of the stroke
     * @return returns the size of the stroke
     */
    public double getStrokeSize() {
        return strokeSize;
    }

    /**
     * returns the color of the stroke
     * @return returns the color of the stroke
     */
    public Paint getStrokeColor() {
        return strokeColor;
    }

    /**
     * returns true|false depending on if they want the shape to be filled
     * @return returns true|false depending on if they want the shape to be filled
     */
    public boolean isFilled() {
        return isFilled;
    }

    /**
     * returns the color of the fill for the shape
     * @return returns the color of the fill for the shape
     */
    public Paint getFillColor() {
        return fillColor;
    }

    @Override
    public String toString() {
        return "ShapeSettings{" +
                "strokeSize=" + strokeSize +
                ", strokeColor=" + strokeColor +
                ", isFilled=" + isFilled +
                ", fillColor=" + fillColor +
                '}';
    }
}
