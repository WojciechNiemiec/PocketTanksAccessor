package com.wniemiec.cheat;

import com.wniemiec.cheat.logic.PocketTanksCheater;
import javafx.application.Application;
import lombok.extern.log4j.Log4j;

@Log4j
public class App {
    public static void main(String[] args) {
        log.info("Application start");
        Application.launch(PocketTanksCheater.class);
    }
}