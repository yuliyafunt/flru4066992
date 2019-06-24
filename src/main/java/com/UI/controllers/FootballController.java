package com.UI.controllers;

import com.UI.views.ConditionView;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import javax.inject.Singleton;

@Singleton
public class FootballController {

    public VBox conditionContainer;
    public Button addCondition;
    public Button saveFilters;

    public void initialize() {
        setup();
        addCondition.setOnMouseClicked(event -> conditionContainer.getChildren().add(new ConditionView(conditionContainer.getMaxWidth()).getView()));
    }

    private void setup() {
        conditionContainer.setPadding(new Insets(5.0));
        conditionContainer.setSpacing(5.0);
    }

    public void test() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Test");
        alert.show();
    }
}
