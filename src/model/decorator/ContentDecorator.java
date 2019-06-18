package model.decorator;

import javafx.scene.control.Alert;

/**
 * Decorates content/text in the Alert popup
 *
 *@author Chauncey Brown-Castro
 *@version 1.0
 */
public class ContentDecorator implements IDecorate
{
    private IDecorate subject;
    private String author1;
    private Alert alert;

    /**
     * Constructor for the ContentDecorator object
     *
     * @param subject IDecorator interface object
     * @param alert to be decorated
     * @param author1 first author of the app
     */
    public ContentDecorator(IDecorate subject, Alert alert, String author1)
    {

        this.subject = subject;
        this.author1 = author1;
        this.alert = alert;
    }

    @Override
    public Alert generateAlert()
    {
        subject.generateAlert();
        alert.setContentText("By: \n" + author1);
        return alert;
    }

    @Override
    public String toString()
    {
        return "ContentDecorator{" +
                "subject=" + subject +
                ", author1='" + author1 + '\'' +
                ", alert=" + alert +
                '}';
    }
}
