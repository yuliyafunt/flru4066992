package com.UI.views;

import com.UI.components.ToggleComponent;
import com.core.Condition;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ConditionView {

    private final AnchorPane rootPane = new AnchorPane();
    private final GridPane conditionRow = new GridPane();
    private final ChoiceBox<String> choiceBox = new ChoiceBox<>();
    private final TextField comment = new TextField();
    private final ToggleComponent toggleComponent = new ToggleComponent();

    private final double maxWidth;

    public ConditionView(double maxWidth) {
        this.maxWidth = maxWidth;
    }

    public Node getView() {
        setup();

        Set<String> availableConditions = Arrays.stream(Condition.values()).map(c -> c.val).collect(toSet());
        choiceBox.setItems(FXCollections.observableArrayList(availableConditions));
        conditionRow.add(choiceBox, 0, 0);
        conditionRow.add(toggleComponent.getView(), 1, 0);

        TextField score = new TextField();
        score.setPrefWidth(50.0);

        conditionRow.add(score, 2, 0);

        comment.setPromptText("Комментарий который бот пришлет вместе с уведомлением");
        comment.setPrefWidth(600.0);
        conditionRow.add(comment, 0, 1, 5, 1);

        rootPane.getChildren().add(conditionRow);
        return rootPane;
    }

    private void setup() {
        // add setup preferences here
        conditionRow.setVgap(5.0);
        conditionRow.setHgap(5.0);
    }

    public Condition getCurrentCondition() {
        return Condition.find(choiceBox.getSelectionModel().getSelectedItem());
    }

    public String getComment() {
        return comment.getText();
    }
}
