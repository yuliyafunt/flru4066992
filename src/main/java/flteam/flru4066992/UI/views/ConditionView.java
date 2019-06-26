package flteam.flru4066992.UI.views;

import flteam.flru4066992.UI.components.ToggleComponent;
import flteam.flru4066992.core.BetType;
import flteam.flru4066992.core.Context;
import flteam.flru4066992.core.conditions.Condition;
import flteam.flru4066992.core.conditions.Operator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ConditionView extends AnchorPane {

    private final GridPane conditionRow = new GridPane();
    private final ChoiceBox<String> choiceBox = new ChoiceBox<>();
    private final TextField score = new TextField();
    private final ToggleComponent toggleComponent = new ToggleComponent();
    private final Button deleteBtn = new Button("Удалить условие");

    public ConditionView(VBox container, Context context, BetType type) {
        deleteBtn.setOnMouseClicked(event -> {
            container.getChildren().remove(this);
            context.removeFilter(type, getCurrentCondition());
        });
        buildView();
    }

    private void buildView() {
        setup();

        Set<String> availableConditions = Arrays.stream(Condition.values()).map(c -> c.val).collect(toSet());
        choiceBox.setItems(FXCollections.observableArrayList(availableConditions));
        conditionRow.add(choiceBox, 0, 0);
        conditionRow.add(toggleComponent.getView(), 1, 0);

        score.setPrefWidth(50.0);

        conditionRow.add(score, 2, 0);
        conditionRow.add(deleteBtn, 3, 0);

        getChildren().add(conditionRow);
    }

    private void setup() {
        // add setup preferences here
        conditionRow.setVgap(5.0);
        conditionRow.setHgap(5.0);

        // add red border if value not integer
        score.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        score.setOnKeyReleased(event -> {
            ObservableList<String> styles = score.getStyleClass();
            try {
                Integer.parseInt(score.getText());
                styles.remove("error");
            } catch (NumberFormatException e) {
                if (!styles.contains("error")) {
                    styles.add("error");
                }
            }
        });
    }

    /**
     * Call {@link #validate()} before use this method
     */
    public Operator getOperator() {
        return toggleComponent.getOperator();
    }

    /**
     * Call {@link #validate()} before use this method
     */
    public Condition getCurrentCondition() {
        return Condition.find(choiceBox.getSelectionModel().getSelectedItem());
    }

    /**
     * Call {@link #validate()} before use this method
     */
    public int getTextFieldValue() {
        return Integer.parseInt(score.getText());
    }

    public boolean validate() {
        return getCurrentCondition() != null
                && toggleComponent.getOperator() != null
                && getTextFieldValue() >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConditionView that = (ConditionView) o;
        return Objects.equals(conditionRow, that.conditionRow) &&
                Objects.equals(choiceBox, that.choiceBox) &&
                Objects.equals(score, that.score) &&
                Objects.equals(toggleComponent, that.toggleComponent) &&
                Objects.equals(deleteBtn, that.deleteBtn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conditionRow, choiceBox, score, toggleComponent, deleteBtn);
    }
}
