package launcher;

import javafx.application.Application;
import view.DoodleView;

/**
 * This class is the entry point into our Doodle Program
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class Launcher {

    /**
     * This is the entry point into our Doodle Program
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Application.launch(DoodleView.class);
    }
}
