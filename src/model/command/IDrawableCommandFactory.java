package model.command;

import javafx.scene.canvas.Canvas;

/**
 * This class is a interface that ensures any object that implements it will
 * inherit the draw() command for drawing shapes, and a factory method that will return
 * a randomized version of itself
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public interface IDrawableCommandFactory {

    /**
     * This method will define a shape is drawn to the canvas
     */
    void draw();

    /**
     * This function creates a new random shape and returns it
     * @param canvas the canvas to be drawn on
     * @return returns a new IDrawableCommandFactory that's randomly generated and can be drawn
     */
    IDrawableCommandFactory getRandomShape(Canvas canvas);
}
