package com.wniemiec.cheat.logic;

import com.wniemiec.cheat.ptanks.PocketTanksAccessor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class PocketTanksCheater extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        URL resource = getClass().getResource("window.fxml");
        Parent root = FXMLLoader.load(resource);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Pocket tanks cheater");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        PocketTanksAccessor.closeProcess();
    }
}
