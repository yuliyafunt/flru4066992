package flteam.flru4066992.UI.controllers;

import flteam.flru4066992.UI.views.ConditionView;
import flteam.flru4066992.core.Context;
import flteam.flru4066992.core.conditions.sportspecific.BasketConditions;
import flteam.flru4066992.core.conditions.sportspecific.FootballConditions;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static flteam.flru4066992.core.BetType.BASKETBALL;
import static java.util.stream.Collectors.toSet;

@Singleton
public class BasketballController extends AbstractController {

    public Tab tab1;
    public Tab tab2;
    public Tab tab3;
    public Tab tab4;
    public Tab tab5;
    public Tab tab6;

    public Button b1;
    public Button b2;
    public Button b3;
    public Button b4;
    public Button b5;
    public Button b6;

    public VBox v1;
    public VBox v2;
    public VBox v3;
    public VBox v4;
    public VBox v5;
    public VBox v6;

    public TabPane tabPane;
    public Button saveFilters;
    public TextArea commentArea;

    @Inject
    private Context context;

    public void initialize() {
        setup();
    }

    private void setup() {
        Set<String> possibleValues = Arrays.stream(BasketConditions.Type.values()).map(t -> t.value).collect(toSet());
        int viewsCount = possibleValues.size();

        b1.setOnMouseClicked(event -> {
            if (v1.getChildren().size() < viewsCount) {
                ConditionView view = new ConditionView(this, v1, possibleValues);
                v1.getChildren().add(view);
            }
        });
        b2.setOnMouseClicked(event -> {
            if (v2.getChildren().size() < viewsCount) {
                ConditionView view = new ConditionView(this, v2, possibleValues);
                v2.getChildren().add(view);
            }
        });
        b3.setOnMouseClicked(event -> {
            if (v3.getChildren().size() < viewsCount) {
                ConditionView view = new ConditionView(this, v3, possibleValues);
                v3.getChildren().add(view);
            }
        });
        b4.setOnMouseClicked(event -> {
            if (v4.getChildren().size() < viewsCount) {
                ConditionView view = new ConditionView(this, v4, possibleValues);
                v4.getChildren().add(view);
            }
        });
        b5.setOnMouseClicked(event -> {
            if (v5.getChildren().size() < viewsCount) {
                ConditionView view = new ConditionView(this, v5, possibleValues);
                v5.getChildren().add(view);
            }
        });
        b6.setOnMouseClicked(event -> {
            if (v6.getChildren().size() < viewsCount) {
                ConditionView view = new ConditionView(this, v6, possibleValues);
                v6.getChildren().add(view);
            }
        });

        saveFilters.setOnMouseClicked(event -> saveFilter());
    }

    private void saveFilter() {
        saveFiltersHelper(v1);
        saveFiltersHelper(v2);
        saveFiltersHelper(v3);
        saveFiltersHelper(v4);
        saveFiltersHelper(v5);
        saveFiltersHelper(v6);

        context.setFilterComment(BASKETBALL, commentArea.getText());
    }

    private void saveFiltersHelper(VBox v) {
        // check only condition rows. don't care about other elements

        List<ConditionView> conditionViews = validateFilters(v1);
        if (conditionViews == null) return;

        for (ConditionView view : conditionViews) {
            int textFieldValue = view.getTextFieldValue();
            FootballConditions conditionInstance = FootballConditions.Type.find(view.getCurrentCondition());
            acceptCondition(v1.getId(), view, textFieldValue, conditionInstance);
        }
    }

    @Override
    public void delete(String tabId, String value) {
        context.removeFilter(BASKETBALL, tabId, FootballConditions.Type.find(value));
    }
}
