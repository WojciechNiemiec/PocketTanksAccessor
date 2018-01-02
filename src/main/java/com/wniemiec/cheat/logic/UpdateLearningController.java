package com.wniemiec.cheat.logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class UpdateLearningController {
    private CheatContext context = CheatContext.getInstance();

    @FXML
    private GridPane gridPane;

    @FXML
    private Label windSpeed;

    @FXML
    private Label verticalDistance;

    @FXML
    private Label horizontalDistance;

    @FXML
    private TextField power;

    @FXML
    private TextField angle;

    public void apply(ActionEvent actionEvent) {
        power.setText(String.valueOf(context.getPlayerOne().getPower()));
        angle.setText(String.valueOf(context.getPlayerOne().getAngle()));
    }

    public void save(ActionEvent actionEvent) {
        context.addLearningVector(new LearningVector(context, Double.valueOf(power.getText()), Double.valueOf(angle.getText())));
        context.learn();
        close();
    }

    private void close() {
        Window window = this.gridPane.getScene().getWindow();
        if (window instanceof Stage) {
            ((Stage)window).close();
        }
    }

    @FXML
    public void initialize() {
        setText(horizontalDistance, context.getHorizontalDistance());
        setText(verticalDistance, context.getVerticalDistance());
        setText(windSpeed, context.getWindSpeed());
    }

    private void setText(Label label, Object value) {
        label.setText(String.valueOf(value));
    }
}
