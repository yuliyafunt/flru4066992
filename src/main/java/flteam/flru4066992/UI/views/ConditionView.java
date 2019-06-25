package flteam.flru4066992.UI.views;

import flteam.flru4066992.UI.components.ToggleComponent;
import flteam.flru4066992.core.conditions.Condition;
import flteam.flru4066992.core.conditions.Operator;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ConditionView {

    private final AnchorPane rootPane = new AnchorPane();
    private final GridPane conditionRow = new GridPane();
    private final ChoiceBox<String> choiceBox = new ChoiceBox<>();
    private final TextField score = new TextField();
    private final ToggleComponent toggleComponent = new ToggleComponent();

    Image closeImg = new Image(getClass().getResourceAsStream("/close.png"));
    ImageView imgView = new ImageView(closeImg);

    public ConditionView() {
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

//        imgView.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//
//            }
//        });

        conditionRow.add(imgView, 4, 0);
        rootPane.getChildren().add(conditionRow);
    }

    public Node getView() {
        return rootPane;
    }

    private void setup() {
        // add setup preferences here
        conditionRow.setVgap(5.0);
        conditionRow.setHgap(5.0);
    }

    public Operator getOperator() {
        return toggleComponent.getOperator();
    }

    public Condition getCurrentCondition() {
        return Condition.find(choiceBox.getSelectionModel().getSelectedItem());
    }

    public String getTextFieldValue() {
        return score.getText();
    }
}
