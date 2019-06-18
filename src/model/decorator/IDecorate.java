package model.decorator;

import javafx.scene.control.Alert;

/**
 * Decorator interface
 *
 *@author Chauncey Brown-Castro
 *@version 1.0
 */
public interface IDecorate
{
    /**
     * Decorates Alert object
     *
     * @return Decorated Alert object
     */
    Alert generateAlert();
}
