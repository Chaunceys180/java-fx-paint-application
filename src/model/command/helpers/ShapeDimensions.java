package model.command.helpers;

/**
 * This class is a helper class to help describe a shapes dimensions for our paint application
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class ShapeDimensions {

    private double startX;
    private double startY;
    private double endX;
    private double endY;

    /**
     * This method is our class constructor
     * @param startX starting x coordinate
     * @param startY starting y coordinate
     * @param endX ending x coordinate
     * @param endY ending y coordinate
     */
    public ShapeDimensions(double startX, double startY, double endX, double endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    /**
     * returns the start x coordinate
     * @return returns the start x coordinate
     */
    public double getStartX() {
        return startX;
    }

    /**
     * sets the start x coordinate
     * @param startX sets the start x coordinate
     */
    public void setStartX(double startX) {
        this.startX = startX;
    }

    /**
     * returns the start y coordinate
     * @return returns the start y coordinate
     */
    public double getStartY() {
        return startY;
    }

    /**
     * sets the start y coordinate
     * @param startY sets the start y coordinate
     */
    public void setStartY(double startY) {
        this.startY = startY;
    }

    /**
     * returns the end x coordinate
     * @return returns the end x coordinate
     */
    public double getEndX() {
        return endX;
    }

    /**
     * sets the end x coordinate
     * @param endX sets the end x coordinate
     */
    public void setEndX(double endX) {
        this.endX = endX;
    }

    /**
     * returns the end y coordinate
     * @return returns the end y coordinate
     */
    public double getEndY() {
        return endY;
    }

    /**
     * sets the end y coordinate
     * @param endY sets the end y coordinate
     */
    public void setEndY(double endY) {
        this.endY = endY;
    }

    @Override
    public String toString() {
        return "ShapeDimensions{" +
                "startX=" + startX +
                ", startY=" + startY +
                ", endX=" + endX +
                ", endY=" + endY +
                '}';
    }
}
