package model.decorator;

import javafx.scene.control.Alert;

/**
 * Decorates Header for the Alert Popup
 *
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class HeaderDecorator implements IDecorate
{
    private IDecorate subject;
    private Alert alert;

    /**
     * Constructor for the HeaderDecorator object
     *
     * @param subject IDecorate interface object
     * @param alert to be decorated
     */
    public HeaderDecorator(IDecorate subject, Alert alert)
    {
        System.out.println(subject.toString());
        this.subject = subject;
        this.alert = alert;
    }

    @Override
    public Alert generateAlert()
    {
        subject.generateAlert();
        alert.setHeaderText("Doodle Application Version 1.0");
        return alert;
    }

    @Override
    public String toString()
    {
        return "HeaderDecorator{" +
                "subject=" + subject +
                ", alert=" + alert +
                '}';
    }
}
