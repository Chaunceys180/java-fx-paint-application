package model.command.helpers;

import java.util.Arrays;

/**
 * This class is a helper class that stores an array of x, and y coordinates for the squiggle path
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class SquiggleCoordinates {

    private double[] coordinateX;
    private double[] coordinateY;

    /**
     * This is the SquiggleCoordinate constructor
     * @param coordinateX an array of x coordinates
     * @param coordinateY an array of y coordinates
     */
    public SquiggleCoordinates(double[] coordinateX, double[] coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    /**
     * returns the array of x coordinates
     * @return returns the array of x coordinates
     */
    public double[] getCoordinateX() {
        return coordinateX;
    }

    /**
     * returns the array of y coordinates
     * @return returns the array of y coordinates
     */
    public double[] getCoordinateY() {
        return coordinateY;
    }

    /**
     * returns the number of coordinate pairs (x, y)
     * @return returns the number of coordinate pairs (x, y)
     */
    public int getNumOfCoordinates() {
        return coordinateX.length;
    }

    @Override
    public String toString() {
        return "SquiggleCoordinates{" +
                "coordinateX=" + Arrays.toString(coordinateX) +
                ", coordinateY=" + Arrays.toString(coordinateY) +
                '}';
    }
}
