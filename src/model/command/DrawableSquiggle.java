package model.command;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import model.command.helpers.ShapeDimensions;
import model.command.helpers.ShapeSettings;
import model.command.helpers.SquiggleCoordinates;

import java.util.Random;

/**
 * This class creates a drawable squiggle that can get drawn to a canvas
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class DrawableSquiggle extends DrawableShape implements IDrawableCommandFactory {

    //can't reliably generate a random squiggle, so store a different shape to be returned
    private IDrawableCommandFactory shape;

    //squiggle path coordinates
    private SquiggleCoordinates coordinates;

    //number of other drawable shapes (Line, Rectangle, Ellipse)
    private static final int NUM_OF_SHAPES = 3;

    /**
     * This is the DrawableSquiggle constructor
     * @param context is the graphics context of the canvas to be drawn on
     * @param shapeSettings is the setting of the shapes colors, and stroke
     * @param shapeDimensions is the size and positioning of the shape on the canvas
     * @param coordinates is the path that the squiggle will be drawn
     */
    public DrawableSquiggle(GraphicsContext context, ShapeSettings shapeSettings,
                            ShapeDimensions shapeDimensions, SquiggleCoordinates coordinates) {
        super(context, shapeSettings, shapeDimensions);
        this.coordinates = coordinates;
    }

    @Override
    public void draw() {
        //get the context and set the shape's settings
        GraphicsContext context = super.getContext();
        context.setStroke(super.getShapeSettings().getStrokeColor());
        context.setLineWidth(super.getShapeSettings().getStrokeSize());
        context.stroke();

        if(super.getShapeSettings().isFilled()) { //if filled, draw with fill settings
            context.setFill(super.getShapeSettings().getFillColor());
            context.fill();
            context.fillPolygon(
                coordinates.getCoordinateX(),
                coordinates.getCoordinateY(),
                coordinates.getNumOfCoordinates()
            );
        }

        //stroked version will always draw
        context.strokePolyline(
                coordinates.getCoordinateX(),
                coordinates.getCoordinateY(),
                coordinates.getNumOfCoordinates()
        );
    }

    @Override
    public IDrawableCommandFactory getRandomShape(Canvas canvas) {
        //build randomized shape dimensions and settings
        ShapeDimensions randomShapeDimension = new ShapeDimensions(
                super.getRandomWidth(canvas), super.getRandomHeight(canvas),
                super.getRandomWidth(canvas), super.getRandomHeight(canvas)
        );

        ShapeSettings shapeSettings = new ShapeSettings(
                super.getRandomStroke(),
                super.getRandomColor(),
                false,
                super.getRandomColor()
        );

        Random random = new Random();
        int option = random.nextInt(NUM_OF_SHAPES);

        //return new random drawable shape using randomized settings and dimensions
        switch (option) {
            case 0 : // line
                shape = new DrawableLine(canvas.getGraphicsContext2D(), shapeSettings, randomShapeDimension);
                break;
            case 1 : // oval
                shape = new DrawableEllipse(canvas.getGraphicsContext2D(), getShapeSettings(), randomShapeDimension);
                break;
            case 2 : // rect
                shape = new DrawableRectangle(canvas.getGraphicsContext2D(), getShapeSettings(), randomShapeDimension);
                break;
        }
        return shape;
    }

    @Override
    public String toString() {
        return "DrawableSquiggle{" +
                "shape=" + shape +
                ", coordinates=" + coordinates +
                '}';
    }
}
