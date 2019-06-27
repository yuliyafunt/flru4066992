package flteam.flru4066992.UI.views;

import flteam.flru4066992.UI.components.ToggleComponent;
import flteam.flru4066992.UI.controllers.AbstractController;
import flteam.flru4066992.core.conditions.Operator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Objects;
import java.util.Set;

public class ConditionView extends AnchorPane {

    private final GridPane conditionRow = new GridPane();
    private final ChoiceBox<String> choiceBox = new ChoiceBox<>();
    private final TextField score = new TextField();
    private final ToggleComponent toggleComponent = new ToggleComponent();
    private final Button deleteBtn = new Button("Удалить условие");
    private final Label accepted = new Label("Добавлено");

    private int stateHash = 0;

    public ConditionView(AbstractController controller, VBox container, Set<String> availableValues) {
        choiceBox.setItems(FXCollections.observableArrayList(availableValues));
        deleteBtn.setOnMouseClicked(event -> {
            container.getChildren().remove(this);
            controller.delete(getCurrentCondition());
        });
        buildView();
    }

    private void buildView() {
        setup();

        conditionRow.add(choiceBox, 0, 0);
        conditionRow.add(toggleComponent, 1, 0);

        score.setPrefWidth(50.0);

        conditionRow.add(score, 2, 0);
        conditionRow.add(deleteBtn, 3, 0);
        conditionRow.add(accepted, 4, 0);

        getChildren().add(conditionRow);
    }

    private void setup() {
        // add setup preferences here
        accepted.setVisible(false);

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
            accepted.setVisible(stateHash == getComponentHash(getCurrentCondition()));
        });
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> accepted.setVisible(stateHash == getComponentHash(newValue)));
//        toggleComponent.setOnMouseClicked(event -> accepted.setVisible(stateHash == getComponentHash()));
    }

    private int getComponentHash(Object conditionValue) {
        return Objects.hash(conditionValue, toggleComponent.getOperator(), score.getText());
    }

    /**
     * Call {@link #validate()} before use this method
     */
    public Operator getOperator() {
        return toggleComponent.getOperator();
    }

    /**
     * Call {@link #validate()} before use this method
     *
     * @return
     */
    public String getCurrentCondition() {
        return choiceBox.getSelectionModel().getSelectedItem();
    }

    /**
     * Call {@link #validate()} before use this method
     */
    public int getTextFieldValue() {
        return Integer.parseInt(score.getText());
    }


    public void accept() {
        accepted.setVisible(true);
    }

    public boolean validate() {
        boolean v = getCurrentCondition() != null
                && toggleComponent.getOperator() != null
                && getTextFieldValue() >= 0;
        if (v) {
            stateHash = getComponentHash(getCurrentCondition());
        }
        return v;
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
