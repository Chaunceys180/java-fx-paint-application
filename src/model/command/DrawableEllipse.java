package model.command;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Ellipse;
import model.command.helpers.ShapeDimensions;
import model.command.helpers.ShapeSettings;

/**
 * This class defines a ellipse that can be drawn to a canvas
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class DrawableEllipse extends DrawableShape implements IDrawableCommandFactory {

    /**
     * This is the DrawableEllipse constructor
     * @param context is the graphics context of the canvas to be drawn on
     * @param shapeSettings is the setting of the shapes colors, and stroke
     * @param shapeDimensions is the size and positioning of the shape on the canvas
     */
    public DrawableEllipse(GraphicsContext context, ShapeSettings shapeSettings, ShapeDimensions shapeDimensions) {
        super(context, shapeSettings, shapeDimensions);
        calculateDraw();
    }

    private void calculateDraw() {
        //create a ellipse object to store dimension translations
        Ellipse oval = new Ellipse();
        double width = super.getShapeDimensions().getEndX() - super.getShapeDimensions().getStartX();
        double height = super.getShapeDimensions().getEndY() - super.getShapeDimensions().getStartY();

        //translate widths accordingly
        if(width < 0) {
            oval.setCenterX(super.getShapeDimensions().getEndX());
            oval.setRadiusX(-width);
        } else {
            oval.setCenterX(super.getShapeDimensions().getStartX());
            oval.setRadiusX(super.getShapeDimensions().getEndX() - super.getShapeDimensions().getStartX());
        }

        //translate heights accordingly
        if(height < 0) {
            oval.setCenterY(super.getShapeDimensions().getEndY());
            oval.setRadiusY(-height);
        } else {
            oval.setCenterY(super.getShapeDimensions().getStartY());
            oval.setRadiusY(super.getShapeDimensions().getEndY() - super.getShapeDimensions().getStartY());
        }

        //properly re-set the shape dimensions as needed
        super.getShapeDimensions().setStartX(oval.getCenterX());
        super.getShapeDimensions().setEndX(oval.getRadiusX());
        super.getShapeDimensions().setStartY(oval.getCenterY());
        super.getShapeDimensions().setEndY(oval.getRadiusY());
    }

    @Override
    public void draw() {
        //get the context and set the shape's settings
        GraphicsContext context = super.getContext();
        context.setStroke(super.getShapeSettings().getStrokeColor());
        context.setLineWidth(super.getShapeSettings().getStrokeSize());

        if(super.getShapeSettings().isFilled()) { //if filled, draw with fill settings
            context.setFill(super.getShapeSettings().getFillColor());

            context.fillOval(
                    super.getShapeDimensions().getStartX(),
                    super.getShapeDimensions().getStartY(),
                    super.getShapeDimensions().getEndX(),
                    super.getShapeDimensions().getEndY()
            );
        }

        //stroked version will always draw
        context.strokeOval(
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
        return new DrawableEllipse(canvas.getGraphicsContext2D(), shapeSettings, randomShapeDimension);
    }
}
