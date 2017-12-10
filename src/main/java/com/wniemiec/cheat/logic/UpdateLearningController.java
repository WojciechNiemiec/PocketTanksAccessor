package com.wniemiec.cheat.logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UpdateLearningController {
    private CheatContext context = CheatContext.getInstance();

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
