package flteam.flru4066992.UI.controllers;

import flteam.flru4066992.UI.views.ConditionView;
import flteam.flru4066992.core.Context;
import flteam.flru4066992.core.conditions.sportspecific.FootballConditions;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static flteam.flru4066992.core.BetType.FOOTBALL;
import static java.util.stream.Collectors.toSet;

@Singleton
public class FootballController extends AbstractController {

    @Inject
    private Context context;

    public VBox conditionContainer;
    public Button addCondition;
    public Button saveFilters;
    public TextArea commentArea;

    public void initialize() {
        setup();
    }

    private void setup() {
        conditionContainer.setPadding(new Insets(5.0));
        conditionContainer.setSpacing(5.0);

        Set<String> possibleValues = Arrays.stream(FootballConditions.Type.values()).map(t -> t.value).collect(toSet());
        int viewsCount = possibleValues.size();
        addCondition.setOnMouseClicked(event -> {
            if (conditionContainer.getChildren().size() < viewsCount) {
                ConditionView view = new ConditionView(this, conditionContainer, possibleValues);
                conditionContainer.getChildren().add(view);
            }
        });
        saveFilters.setOnMouseClicked(event -> saveFilter());
    }

    private void saveFilter() {
        // check only condition rows. don't care about other elements
        List<ConditionView> conditionViews = validateFilters(conditionContainer);
        if (conditionViews == null) return;

        for (ConditionView view : conditionViews) {
            int textFieldValue = view.getTextFieldValue();
            FootballConditions conditionInstance = FootballConditions.Type.find(view.getCurrentCondition());
            acceptCondition(view, textFieldValue, conditionInstance);
        }
        context.setFilterComment(FOOTBALL, commentArea.getText());
    }

    @Override
    public void delete(String value) {
        context.removeFilter(FOOTBALL, FootballConditions.Type.find(value));
    }
}
