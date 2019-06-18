package model.command;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import model.command.helpers.ShapeDimensions;
import model.command.helpers.ShapeSettings;

/**
 * This class defines a line that can be drawn to a canvas
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class DrawableLine extends DrawableShape implements IDrawableCommandFactory {

    /**
     * This is the DrawableLine constructor
     * @param context is the graphics context of the canvas to be drawn on
     * @param shapeSettings is the setting of the shapes colors, and stroke
     * @param shapeDimensions is the size and positioning of the shape on the canvas
     */
    public DrawableLine(GraphicsContext context, ShapeSettings shapeSettings, ShapeDimensions shapeDimensions) {
        super(context, shapeSettings, shapeDimensions);
    }

    @Override
    public void draw() {
        //get the context, set line settings
        GraphicsContext context = super.getContext();
        context.setLineWidth(super.getShapeSettings().getStrokeSize());
        context.setStroke(super.getShapeSettings().getStrokeColor());

        //draw the line based on the shape dimensions
        context.strokeLine(
            super.getShapeDimensions().getStartX(),
            super.getShapeDimensions().getStartY(),
            super.getShapeDimensions().getEndX(),
            super.getShapeDimensions().getEndY()
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
            super.getRandomStroke(), super.getRandomColor(), false, super.getRandomColor()
        );

        //return new line using randomized settings and dimensions
        return new DrawableLine(canvas.getGraphicsContext2D(), shapeSettings, randomShapeDimension);
    }
}
