package flteam.flru4066992.UI.controllers;

import flteam.flru4066992.UI.views.ConditionView;
import flteam.flru4066992.core.Context;
import flteam.flru4066992.core.conditions.Condition;
import flteam.flru4066992.core.conditions.Expression;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collection;

import static flteam.flru4066992.core.BetType.FOOTBALL;

@Singleton
public class FootballController {

    private Collection<ConditionView> views = new ArrayList<>();

    public VBox conditionContainer;
    public Button addCondition;
    public Button saveFilters;

    @Inject
    private Context context;

    public void initialize() {
        setup();

        addCondition.setOnMouseClicked(event -> {
            ConditionView view = new ConditionView();
            views.add(view);
            conditionContainer.getChildren().add(view.getView());
        });

        saveFilters.setOnMouseClicked(event -> saveFilter());
    }

    private void setup() {
        conditionContainer.setPadding(new Insets(5.0));
        conditionContainer.setSpacing(5.0);
    }

    @FXML
    private void saveFilter() {
        for (ConditionView view : views) {
            Condition condition = view.getCurrentCondition();
            String textFieldValue = view.getTextFieldValue();
            switch (view.getOperator()) {
                case EQUALS:
                    saveFilter0(Expression.eq(condition, textFieldValue));
                    break;
                case GT:
                    saveFilter0(Expression.greater(condition, textFieldValue));
                    break;
                case LT:
                    saveFilter0(Expression.less(condition, textFieldValue));
                    break;
                case GTE:
                    saveFilter0(Expression.greaterOrEq(condition, textFieldValue));
                    break;
                case LTE:
                    saveFilter0(Expression.lessOrEq(condition, textFieldValue));
                    break;
            }
        }
    }

    private void saveFilter0(Expression expression) {
        context.addFilter(FOOTBALL, expression);
    }
}
