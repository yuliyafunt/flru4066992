package com.UI;

import com.UI.controllers.FootballController;
import javafx.fxml.FXML;

import javax.inject.Inject;


public class MainController {

    private final FootballController footballController;

    @Inject
    public MainController(FootballController footballController) {
        this.footballController = footballController;
    }

    public void initialize() {
//        footballController.test();
    }

    private void setup() {
    }
}
