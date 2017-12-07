package com.wniemiec.cheat.logic;

import com.wniemiec.cheat.ptanks.Position;
import com.wniemiec.cheat.ptanks.Tank;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class WindowController {
    private CheatContext context = new CheatContext();

    @FXML
    private Label horizontalDistance;

    @FXML
    private Label verticalDistance;

    @FXML
    private Label windSpeed;

    @FXML
    private Label power;

    @FXML
    private Label angle;


    @FXML
    private TextField leftPositionX;

    @FXML
    private TextField leftPositionY;

    @FXML
    private TextField leftPower;

    @FXML
    private TextField leftAngle;


    @FXML
    private TextField rightPositionX;

    @FXML
    private TextField rightPositionY;

    @FXML
    private TextField rightPower;

    @FXML
    private TextField rightAngle;

    @FXML
    public void refreshProperties(ActionEvent actionEvent) {
        context.refreshProperties();
        setText(horizontalDistance, context.getHorizontalDistance());
        setText(verticalDistance, context.getVerticalDistance());
        setText(windSpeed, context.getWindSpeed());
        setText(power, context.getCountedPower());
        setText(angle, context.getCountedAngle());
    }

    public void compute(ActionEvent actionEvent) {
        context.refreshProperties();
    }

    public void apply(ActionEvent actionEvent) {
        leftPower.setText(power.getText());
        leftAngle.setText(angle.getText());
    }

    public void onEnter(ActionEvent actionEvent) {
        Tank playerOne = context.getPlayerOne();

        playerOne.setPosition(new Position(doubleFrom(leftPositionX), doubleFrom(leftPositionY)));
        playerOne.setPower(doubleFrom(leftPower));
        playerOne.setAngle(doubleFrom(leftAngle));

        Tank playerTwo = context.getPlayerTwo();

        playerTwo.setPosition(new Position(doubleFrom(rightPositionX), doubleFrom(rightPositionY)));
        playerTwo.setPower(doubleFrom(rightPower));
        playerTwo.setAngle(doubleFrom(rightAngle));
    }

    private void setText(Label label, Object value) {
        label.setText(String.valueOf(value));
    }

    private Double doubleFrom(TextField textField) {
        return Double.valueOf(textField.getText());
    }
}
