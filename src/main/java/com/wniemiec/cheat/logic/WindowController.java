package com.wniemiec.cheat.logic;

import com.wniemiec.cheat.ptanks.Position;
import com.wniemiec.cheat.ptanks.Tank;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowController {
    private CheatContext context = CheatContext.getInstance();

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

    public WindowController() {

    }

    @FXML
    public void refreshProperties(ActionEvent actionEvent) {
        context.refreshProperties();
        refreshNetworkProperties();
        refreshTankProperties();
    }

    private void refreshNetworkProperties() {
        setText(horizontalDistance, context.getHorizontalDistance());
        setText(verticalDistance, context.getVerticalDistance());
        setText(windSpeed, context.getWindSpeed());
        setText(power, context.getCountedPower());
        setText(angle, context.getCountedAngle());
    }

    private void refreshTankProperties() {
        Tank playerOne = context.getPlayerOne();
        Position positionOne = playerOne.getPosition();

        setText(leftPositionX, positionOne.x);
        setText(leftPositionY, positionOne.y);
        setText(leftPower, playerOne.getPower());
        setText(leftAngle, playerOne.getAngle());

        Tank playerTwo = context.getPlayerTwo();
        Position positionTwo = playerTwo.getPosition();

        setText(rightPositionX, positionTwo.x);
        setText(rightPositionY, positionTwo.y);
        setText(rightPower, playerTwo.getPower());
        setText(rightAngle, playerTwo.getAngle());
    }
    
    public void compute(ActionEvent actionEvent) {
        context.refreshProperties();
    }

    public void apply(ActionEvent actionEvent) {
        leftPower.setText(power.getText());
        leftAngle.setText(angle.getText());

        Tank playerOne = context.getPlayerOne();
        playerOne.setPower(doubleFrom(leftPower));
        playerOne.setAngle(doubleFrom(leftAngle));
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

    private void setText(TextField textField, Object value) {
        textField.setText(String.valueOf(value));
    }

    private Double doubleFrom(TextField textField) {
        return Double.valueOf(textField.getText());
    }

    public void updateLearning(ActionEvent actionEvent)  throws IOException {
        Stage stage = new Stage();
        Parent load = FXMLLoader.load(getClass().getResource("update_window.fxml"));

        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.setTitle("Add learning vector");
        stage.show();
    }
}
