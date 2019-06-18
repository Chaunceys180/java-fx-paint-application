package model.command;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.command.helpers.ShapeDimensions;
import model.command.helpers.ShapeSettings;
import java.util.Random;

/**
 * This class is the parent class of all drawable shapes,
 * and stores the base information that all shapes should need to be drawn
 *
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class DrawableShape {

    private GraphicsContext context;
    private ShapeSettings shapeSettings;
    private ShapeDimensions shapeDimensions;

    //for getting randomized color values, and sizes
    private Random random = new Random();
    private static final int STROKE_LIMIT = 20; //max size the shape stroke can have

    /**
     * This is the DrawableShape constructor
     * @param context is the graphics context of the canvas to be drawn on
     * @param shapeSettings is the setting of the shapes colors, and stroke
     * @param shapeDimensions is the size and positioning of the shape on the canvas
     */
    public DrawableShape(GraphicsContext context, ShapeSettings shapeSettings, ShapeDimensions shapeDimensions) {
        this.context = context;
        this.shapeSettings = shapeSettings;
        this.shapeDimensions = shapeDimensions;
    }

    /**
     * returns the canvas's graphics context
     * @return returns the canvas's graphics context
     */
    public GraphicsContext getContext() {
        return context;
    }

    /**
     * returns the shapes shape settings such as stroke, color, and if it's filled or not
     * @return returns the shapes shape settings such as stroke, color, and if it's filled or not
     */
    public ShapeSettings getShapeSettings() {
        return shapeSettings;
    }

    /**
     * returns the shapes shape dimensions
     * @return returns the shapes shape dimensions
     */
    public ShapeDimensions getShapeDimensions() {
        return shapeDimensions;
    }

    /**
     * returns a random sized value for stroke size from 1 - 20
     * @return returns a random sized value for stroke size
     */
    public int getRandomStroke() {
        return random.nextInt(STROKE_LIMIT) + 1;
    }

    /**
     * returns a randomly generated color for the shape
     * @return returns a randomly generated color for the shape
     */
    public Color getRandomColor() {
        double red = random.nextFloat();
        double green = random.nextFloat();
        double blue = random.nextFloat();
        return Color.color(red, green, blue);
    }

    /**
     * returns a random width value based on the current size of the canvas
     * so the shape can get a coordinate that is randomly within the canvas
     * @param canvas the canvas to be drawn on
     * @return returns a random width value based on the current size of the canvas
     */
    public double getRandomWidth(Canvas canvas) {
        return random.nextInt((int) Math.floor(canvas.getWidth()));
    }

    /**
     * returns a random height value based on the current size of the canvas
     * so the shape can get a coordinate that is randomly within the canvas
     * @param canvas the canvas to be drawn on
     * @return returns a random height value based on the current size of the canvas
     */
    public double getRandomHeight(Canvas canvas) {
        return random.nextInt((int) Math.floor(canvas.getHeight()));
    }

    @Override
    public String toString() {
        return "DrawableShape{" +
                "context=" + context +
                ", shapeSettings=" + shapeSettings +
                ", shapeDimensions=" + shapeDimensions +
                '}';
    }
}
