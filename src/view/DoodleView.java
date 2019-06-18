package view;

import controller.ShapeController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.decorator.ContentDecorator;
import model.decorator.HeaderDecorator;
import model.decorator.IDecorate;
import model.decorator.TitleDecorator;

import java.util.Arrays;

/**
 * This class creates a GUI that allows a user to draw on a canvas.
 *
 * Patterns Used: Decorator Pattern, Command Pattern, and Memento Pattern
 * @author Chauncey Brown-Castro
 * @version 1.0
 */
public class DoodleView extends Application {

    public static final int WIN_WIDTH = 1000;
    public static final int WIN_HEIGHT = 600;
    public static final int SHAPE_ICON_SIZE = 20;
    public static final int MAX_STROKE = 20;
    public static final int MIN_STROKE = 1;
    public static final String AUTHOR_EMAIL_1 = "Chauncey Brown-Castro <chaunceys180@gmail.com>";

    //drawing on the canvas
    private Canvas canvas;

    //selecting shapes
    private ToggleGroup shapeGroup;

    //shape settings
    private ColorPicker fillColorPicker = new ColorPicker();
    private ColorPicker strokeColorPicker = new ColorPicker();
    private Slider strokeSlider;
    private CheckBox filledCheckbox;

    //array for polyLine
    private double[] xCoords;
    private double[] yCoords;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Doodle Program");
        stage.setScene(getPrimaryScene());
        stage.show();
    }

    private Scene getPrimaryScene() {
        BorderPane mainPanel = new BorderPane();

        VBox top = new VBox();
        top.getChildren().addAll(buildMenu(), getToolbar());

        //set the primary regions
        mainPanel.setTop(top);
        mainPanel.setCenter(getCanvas());

        Scene scene = new Scene(mainPanel, WIN_WIDTH, WIN_HEIGHT);
        scene.getStylesheets().add("styles.css");

        return scene;
    }

    private MenuBar buildMenu() {
        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu draw = new Menu("Draw");
        Menu help = new Menu("Help");

        fileMenu(file);
        editMenu(edit);
        drawMenu(draw);
        help(help);

        menuBar.getMenus().addAll(file, edit, draw, help);
        return menuBar;
    }

    private void fileMenu(Menu file) {
        MenuItem quit = new MenuItem("Quit");
        quit.setOnAction(event -> Platform.exit());
        file.getItems().add(quit);
    }

    private void editMenu(Menu edit) {
        MenuItem undo = new MenuItem("Undo");
        undo.setOnAction(event -> undoOperation());

        MenuItem redo = new MenuItem("Redo");
        redo.setOnAction(event -> redoOperation());

        edit.getItems().addAll(undo, redo);
    }

    private void drawMenu(Menu draw) {
        Menu shapesMenu = new Menu("Shape Tools");

        MenuItem[] shapes = {
                new MenuItem("Line"), new MenuItem("Oval"),
                new MenuItem("Rectangle"), new MenuItem("Squiggle"),
                new MenuItem("Random"),
        };

        for (MenuItem item : shapes) {
            item.setOnAction(event -> selectButton(item));
        }

        shapesMenu.getItems().addAll(shapes);

        MenuItem clear = new MenuItem("Clear Shapes");
        clear.setOnAction(event -> newCanvas());
        draw.getItems().addAll(shapesMenu, clear);
    }

    private void help(Menu about) {
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setOnAction(event -> alertPopup());
        about.getItems().addAll(aboutItem);
    }

    private Parent getToolbar() {
        HBox panel = new HBox();
        panel.setId("toolbar-main");
        panel.getChildren().addAll(buildShapeSection(), buildSettings(), buildEdit());

        return panel;
    }

    private HBox buildShapeSection() {
        HBox shapesPanel = new HBox();
        shapesPanel.setId("toolbar-shapes");

        String[] shapes = {"Line", "Oval", "Rectangle", "Squiggle", "Random"};
        ToggleButton[] buttons = new ToggleButton[shapes.length];
        shapeGroup = new ToggleGroup();

        for (int i = 0; i < shapes.length; i++) {
            buttons[i] = getImageToggleButton(shapes[i]);
            buttons[i].setTooltip(new Tooltip(shapes[i]));
            buttons[i].setId(shapes[i]); //making buttons identifiable
        }

        buttons[0].setSelected(true);
        shapeGroup.getToggles().addAll(buttons);
        shapesPanel.getChildren().addAll(buttons);

        return shapesPanel;
    }

    private HBox buildSettings() {
        HBox settingsPanel = new HBox();
        settingsPanel.setId("toolbar-settings");

        styleColorPicker(fillColorPicker);
        styleColorPicker(strokeColorPicker);

        VBox strokeBox = new VBox();
        Label strokeLabel = new Label("Stroke: 1");
        strokeSlider = new Slider();
        strokeBox.getChildren().addAll(strokeSlider, strokeLabel);

        strokeSlider.setMin(MIN_STROKE);
        strokeSlider.setMax(MAX_STROKE);
        strokeSlider.valueProperty().addListener((observable, oldV, newV) ->
                strokeLabel.setText("Stroke: " + numToInt(newV)));

        filledCheckbox = new CheckBox("Filled");

        settingsPanel.getChildren().addAll(new Label("Fill:"), fillColorPicker,
                new Label("Stroke:"), strokeColorPicker, strokeBox, filledCheckbox);

        return settingsPanel;
    }

    private HBox buildEdit() {
        HBox editPanel = new HBox();
        editPanel.setId("toolbar-edits");

        String[] edits = {"undo", "redoShape"};
        Button[] buttons = new Button[edits.length];

        for (int i = 0; i < edits.length; i++) {
            buttons[i] = getImageButton(edits[i]);
        }

        buttons[0].addEventHandler(MouseEvent.MOUSE_CLICKED, event -> undoOperation());
        buttons[1].addEventHandler(MouseEvent.MOUSE_CLICKED, event -> redoOperation());

        editPanel.getChildren().addAll(buttons);

        return editPanel;
    }

    private void styleColorPicker(ColorPicker picker) {
        picker.getStyleClass().add(ColorPicker.STYLE_CLASS_BUTTON);
        picker.setValue(Color.BLACK);
    }

    private int numToInt(Number value)
    {
        return (int) Math.floor(value.doubleValue());
    }

    private ImageView getButtonIcon(String text) {
        String path;
        path = System.getProperty("user.dir") + "\\src\\assets\\" + text.toLowerCase() + ".png";
        System.out.println(path);
        ImageView image = new ImageView(path);
        image.setFitHeight(SHAPE_ICON_SIZE);
        image.setFitWidth(SHAPE_ICON_SIZE);
        return image;
    }

    private ToggleButton getImageToggleButton(String text) {
        ToggleButton result = new ToggleButton();
        result.setGraphic(getButtonIcon(text));
        return result;
    }

    private Button getImageButton(String text) {
        Button result = new Button();
        result.setGraphic(getButtonIcon(text));
        return result;
    }

    private Parent getCanvas() {
        VBox box = new VBox();

        canvas = new Canvas();
        canvas.setStyle("-fx-background-color: black");
        canvas.widthProperty().bind(box.widthProperty());
        canvas.heightProperty().bind(box.heightProperty());

        setCanvasEventHandlers();

        box.getChildren().add(canvas);

        return box;
    }

    private void setCanvasEventHandlers() {

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            //set starting coordinates for new shape
            xCoords = new double[] {event.getX()};
            yCoords = new double[] {event.getY()};
            ShapeController.setInitXAndY(event.getX(), event.getY());
            ToggleButton button = (ToggleButton) shapeGroup.getSelectedToggle();

            if(button.getId().equals("Random")) {
                setColors();
                ShapeController.drawRandomShape(event, canvas);
            }
        });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            //clear the canvas of old stuff
            clearCanvas();

            //remember only the final shape, and draw them to the screen
            ShapeController.drawAllShapes();

            recordCoordinates(event);

            //determine shape to draw
            ToggleButton button = (ToggleButton) shapeGroup.getSelectedToggle();
            buildShape(button.getId(), event);
        });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            ShapeController.clearRedo();
            saveHistory();
            ShapeController.drawAllShapes();
        });
    }

    private void recordCoordinates(MouseEvent event) {
        //record the new coords for polyLine
        double[] tempX = xCoords;
        double[] tempY = yCoords;

        xCoords = new double[xCoords.length+1];
        yCoords = new double[yCoords.length+1];

        //copy over all the elements
        for(int i = 0; i < tempX.length; i++) {
            xCoords[i] = tempX[i];
            yCoords[i] = tempY[i];
        }

        //add in the new coordinate
        xCoords[xCoords.length-1] = event.getX();
        yCoords[yCoords.length-1] = event.getY();
    }

    private void undoOperation() {
        ShapeController.popShape();
        clearCanvas();
        ShapeController.drawAllShapes();
    }

    private void redoOperation() {
        ShapeController.redoShape();
        clearCanvas();
        ShapeController.drawAllShapes();
    }

    private void saveHistory() {
        ToggleButton button = (ToggleButton) shapeGroup.getSelectedToggle();
        if(button != null) {
            if(ShapeController.getShape() != null) {
                ShapeController.push(ShapeController.getShape());
            }
        }
    }

    private void setColors() {
        ShapeController.setColorSettings(
            strokeSlider.getValue(),
            strokeColorPicker.getValue(),
            filledCheckbox.isSelected(),
            fillColorPicker.getValue()
        );
    }

    private void buildShape(String shape, MouseEvent event) {
        setColors();
        ShapeController.recordCoordinates(xCoords, yCoords);
        ShapeController.drawShape(shape, event, canvas);
    }

    private void clearCanvas() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void newCanvas() {
        clearCanvas();
        ShapeController.clearAll();
    }

    private void selectButton(MenuItem menuItem) {

        ObservableList<Toggle> buttons = shapeGroup.getToggles();
        for(Toggle toggle : buttons) {
            ToggleButton toggleButton = (ToggleButton) toggle;
            if(toggleButton.getId().equals(menuItem.getText())) {
                toggleButton.setSelected(true);
            }
        }
    }

    private void alertPopup() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        IDecorate alertPopup =
            new ContentDecorator(
                new HeaderDecorator(
                    new TitleDecorator(alert),
                    alert),
                    alert,
                    AUTHOR_EMAIL_1);

        alert = alertPopup.generateAlert();
        alert.showAndWait();
    }

    @Override
    public String toString() {
        return "DoodleView{" +
                "canvas=" + canvas +
                ", shapeGroup=" + shapeGroup +
                ", fillColorPicker=" + fillColorPicker +
                ", strokeColorPicker=" + strokeColorPicker +
                ", strokeSlider=" + strokeSlider +
                ", filledCheckbox=" + filledCheckbox +
                ", xCoords=" + Arrays.toString(xCoords) +
                ", yCoords=" + Arrays.toString(yCoords) +
                '}';
    }
}
