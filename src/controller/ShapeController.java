package controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import model.command.*;
import model.command.helpers.*;
import java.util.Random;
import java.util.Stack;

/**
 * This class controls all interaction between drawable shapes in the
 * model layer, and the doodle application in the view layer
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class ShapeController {

    //shape for the undo redoShape stacks
    private static IDrawableCommandFactory shape;

    //undo and redoShape
    private static Stack<IDrawableCommandFactory> undo = new Stack<>();
    private static Stack<IDrawableCommandFactory> redo = new Stack<>();

    //coordinates that need to be passed along
    private static double startX;
    private static double endX;
    private static double startY;
    private static double endY;

    private static double[] xCoordinates;
    private static double[] yCoordinates;

    //shape settings that need to be passed along
    private static ShapeSettings shapeSettings;

    //number of shapes (Line, Oval, Rectangle)
    private static final int NUM_OF_SHAPES = 3;

    private ShapeController() {
        //do nothing
    }

    /**
     * @param shapeType is the shape type to be drawn
     * @param event is the mouse event object
     * @param canvas is the canvas to be drawn on
     */
    public static void drawShape(String shapeType, MouseEvent event, Canvas canvas) {
        switch (shapeType) {
            case "Line" :
                drawLine(event, canvas);
                break;
            case "Oval" :
                drawEllipse(event, canvas);
                break;
            case "Rectangle" :
                drawRectangle(event, canvas);
                break;
            case "Squiggle" :
                drawSquiggle(event, canvas);
                break;
        }
    }

    private static void drawLine(MouseEvent event, Canvas canvas) {
        setEndingXAndY(event);
        shape = new DrawableLine(
            canvas.getGraphicsContext2D(),
            getShapeSettings(),
            getShapeDimensions()
        );
        shape.draw();
    }

    private static void drawEllipse(MouseEvent event, Canvas canvas) {
        setEndingXAndY(event);
        shape = new DrawableEllipse(
                canvas.getGraphicsContext2D(),
                getShapeSettings(),
                getShapeDimensions()
        );
        shape.draw();
    }

    private static void drawRectangle(MouseEvent event, Canvas canvas) {
        setEndingXAndY(event);
        shape = new DrawableRectangle(
                canvas.getGraphicsContext2D(),
                getShapeSettings(),
                getShapeDimensions()
        );
        shape.draw();
    }

    /**
     * This function draws a squiggle, make sure the controller calls recordCoordinates(x[], y[])
     * so that the squiggle has a path to be drawn
     * @param event mouse event
     * @param canvas is the canvas to be drawn on
     */
    private static void drawSquiggle(MouseEvent event, Canvas canvas) {
        setEndingXAndY(event);
        shape = new DrawableSquiggle(
            canvas.getGraphicsContext2D(),
            getShapeSettings(),
            getShapeDimensions(),
            getSquiggleCoordinates()
        );
        shape.draw();
    }

    /**
     * This function draws a random shape
     * @param event mouse event
     * @param canvas is the canvas to be drawn on
     */
    public static void drawRandomShape(MouseEvent event, Canvas canvas) {
        Random random = new Random();
        int option = random.nextInt(NUM_OF_SHAPES);
        setEndingXAndY(event);

        //determine what shape to draw
        switch (option) {
            case 0 : // line
                shape = new DrawableLine(canvas.getGraphicsContext2D(), getShapeSettings(), getShapeDimensions());
                break;
            case 1 : // oval
                shape = new DrawableEllipse(canvas.getGraphicsContext2D(), getShapeSettings(), getShapeDimensions());
                break;
            case 2 : // rect
                shape = new DrawableRectangle(canvas.getGraphicsContext2D(), getShapeSettings(), getShapeDimensions());
                break;
        }

        //get the random shape to draw, and then draw it
        shape = shape.getRandomShape(canvas);
        shape.draw();
    }

    private static void setEndingXAndY(MouseEvent event) {
        endX = event.getX();
        endY = event.getY();
    }

    private static ShapeSettings getShapeSettings() {
        return shapeSettings;
    }

    private static ShapeDimensions getShapeDimensions() {
        return new ShapeDimensions(startX, startY, endX, endY);
    }

    private static SquiggleCoordinates getSquiggleCoordinates() {
        return new SquiggleCoordinates(xCoordinates, yCoordinates);
    }

    /**
     * This function sets the arrays of x & y coordinates for the squiggle path,
     * and need to be called before drawing a squiggle
     * @param xCoords x coordinates for the squiggle path
     * @param yCoords y coordinates for the squiggle path
     */
    public static void recordCoordinates(double[] xCoords, double[] yCoords) {
        xCoordinates = xCoords;
        yCoordinates = yCoords;
    }

    /**
     * returns a IDrawableCommandFactory shape
     * @return returns a IDrawableCommandFactory shape
     */
    public static IDrawableCommandFactory getShape() {
        return shape;
    }

    /**
     * sets the ShapeSettings object for the shape to be created
     * @param stroke is the stroke size
     * @param strokeColor is the stroke color
     * @param isFilled true|false if the shape will be filled or not
     * @param fillColor is the color of the shapes fill
     */
    public static void setColorSettings(double stroke, Paint strokeColor, boolean isFilled, Paint fillColor){
        shapeSettings = new ShapeSettings(stroke, strokeColor, isFilled, fillColor);
    }

    /**
     * This function sets the first coordinates of a shape
     * @param initX is the first x coordinate
     * @param initY if the first y coordinate
     */
    public static void setInitXAndY(double initX, double initY) {
        startX = initX;
        startY = initY;
    }

    /**
     * This function adds a shape to the undo shape history stack
     * @param shape is the shape to be recorded in the undo history
     */
    public static void push(IDrawableCommandFactory shape) {
        undo.push(shape);
    }

    /**
     * This function removes a shape from the history,
     * and adds it to the redoShape history in-case we want it back
     */
    public static void popShape() {
        if(!undo.isEmpty()) {
            redo.push(undo.pop());
        }
    }

    /**
     * This function retrieves a removed shape and re-adds it to the undo history
     */
    public static void redoShape() {
        if(!redo.isEmpty()) {
            undo.push(redo.pop());
        }
    }

    /**
     * This function gets all shapes in the undo stack history, and draws them to the canvas
     */
    public static void drawAllShapes() {
        if(!undo.isEmpty()) {
            for(IDrawableCommandFactory drawable : undo) {
                drawable.draw();
            }
        }
    }

    /**
     * This function clears all shapes from both undo and redo stacks
     */
    public static void clearAll() {
        clearRedo();
        clearUndo();
    }

    /**
     * This function clears the undo history stack
     */
    public static void clearUndo() {
        undo = new Stack<>();
    }

    /**
     * This function clears the redo history stack
     */
    public static void clearRedo() {
        redo = new Stack<>();
    }
}
