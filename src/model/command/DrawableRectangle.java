package model.command;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import model.command.helpers.ShapeDimensions;
import model.command.helpers.ShapeSettings;

/**
 * This class creates a drawable rectangle that can get drawn to a canvas
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class DrawableRectangle extends DrawableShape implements IDrawableCommandFactory {

    /**
     * This is the DrawableRectangle constructor
     * @param context is the graphics context of the canvas to be drawn on
     * @param shapeSettings is the setting of the shapes colors, and stroke
     * @param shapeDimensions is the size and positioning of the shape on the canvas
     */
    public DrawableRectangle(GraphicsContext context, ShapeSettings shapeSettings, ShapeDimensions shapeDimensions) {
        super(context, shapeSettings, shapeDimensions);
        calculateDraw();
    }

    private void calculateDraw() {
        //create a rectangle object to store dimension translations
        Rectangle rectangle = new Rectangle();
        double width = super.getShapeDimensions().getEndX() - super.getShapeDimensions().getStartX();
        double height = super.getShapeDimensions().getEndY() - super.getShapeDimensions().getStartY();

        //translate widths accordingly
        if(width < 0) {
            rectangle.setX(super.getShapeDimensions().getEndX());
            rectangle.setWidth(-width);
        } else {
            rectangle.setX(super.getShapeDimensions().getStartX());
            rectangle.setWidth(super.getShapeDimensions().getEndX() - super.getShapeDimensions().getStartX());
        }

        //translate heights accordingly
        if(height < 0) {
            rectangle.setY(super.getShapeDimensions().getEndY());
            rectangle.setHeight(-height);
        } else {
            rectangle.setY(super.getShapeDimensions().getStartY());
            rectangle.setHeight(super.getShapeDimensions().getEndY() - super.getShapeDimensions().getStartY());
        }

        //properly re-set the shape dimensions as needed
        super.getShapeDimensions().setStartX(rectangle.getX());
        super.getShapeDimensions().setEndX(rectangle.getWidth());
        super.getShapeDimensions().setStartY(rectangle.getY());
        super.getShapeDimensions().setEndY(rectangle.getHeight());
    }

    @Override
    public void draw() {
        //get the context and set the shape's settings
        GraphicsContext context = super.getContext();
        context.setStroke(super.getShapeSettings().getStrokeColor());
        context.setLineWidth(super.getShapeSettings().getStrokeSize());

        if(super.getShapeSettings().isFilled()) { //if filled, draw with fill settings

            context.setFill(super.getShapeSettings().getFillColor());
            context.fillRect(
                    super.getShapeDimensions().getStartX(),
                    super.getShapeDimensions().getStartY(),
                    super.getShapeDimensions().getEndX(),
                    super.getShapeDimensions().getEndY()
            );
        }

        //stroked version will always draw
        context.strokeRect(
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

        //return new drawable rectangle using randomized settings and dimensions
        return new DrawableRectangle(canvas.getGraphicsContext2D(), shapeSettings, randomShapeDimension);
    }
}
