package model.decorator;

import javafx.scene.control.Alert;

/**
 * Decorates Title for the Alert Popup
 *
 *@author Chauncey Brown-Castro
 *@version 1.0
 */
public class TitleDecorator implements IDecorate
{
    private Alert alert;

    /**
     * Constructor for TitleDecorator object
     *
     * @param alert to be decorated
     */
    public TitleDecorator(Alert alert)
    {
        this.alert = alert;
    }

    @Override
    public Alert generateAlert()
    {
        alert.setTitle("Doodle Application");
        return alert;

    }

    @Override
    public String toString()
    {
        return "TitleDecorator{" +
                "alert=" + alert +
                '}';
    }
}
