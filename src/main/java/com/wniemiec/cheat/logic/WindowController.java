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
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class WindowController {
    private CheatContext context = CheatContext.getInstance();

    @FXML
    private VBox vBox;

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

    public WindowController() { }

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

    public void save(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = getFileChooser("Save learning vectors");
        File file = fileChooser.showSaveDialog(getWindow());

        if (Objects.nonNull(file)) {
            context.saveLearningVectors(file);
        }
    }

    public void open(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        FileChooser fileChooser = getFileChooser("Read learning vectors");

        File file = fileChooser.showOpenDialog(getWindow());
        if (Objects.nonNull(file)) {
            context.loadLearningVectors(file);
            context.learn();
        }
    }

    public void quit(ActionEvent actionEvent) {
        Window window = getWindow();
        if (window instanceof Stage) {
            ((Stage) window).close();
        }
    }

    public void undoUpdate(ActionEvent actionEvent) {
        context.undoUpdate();
    }

    private FileChooser getFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(new File(Serializer.VECTORS_PATH));
        return fileChooser;
    }

    private Window getWindow() {
        return vBox.getScene().getWindow();
    }
}
