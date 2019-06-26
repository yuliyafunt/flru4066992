package flteam.flru4066992.UI.controllers;

import flteam.flru4066992.UI.views.ConditionView;
import flteam.flru4066992.core.Context;
import flteam.flru4066992.core.conditions.Condition;
import flteam.flru4066992.core.conditions.Expression;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

import static flteam.flru4066992.core.BetType.FOOTBALL;

@Singleton
public class FootballController {

    @Inject
    private Context context;

    public VBox conditionContainer;
    public Button addCondition;
    public Button saveFilters;
    public TextArea commentArea;

    private ObservableList<Node> views;

    public void initialize() {
        setup();
        this.views = conditionContainer.getChildren();
    }

    private void setup() {
        conditionContainer.setPadding(new Insets(5.0));
        conditionContainer.setSpacing(5.0);

        int viewsCount = Condition.values().length;
        addCondition.setOnMouseClicked(event -> {
            if (this.views.size() < viewsCount) {
                ConditionView view = new ConditionView(conditionContainer, context, FOOTBALL);
                conditionContainer.getChildren().add(view);
            }
        });

        saveFilters.setOnMouseClicked(event -> saveFilter());
    }

    private void saveFilter() {
        // check only condition rows. don't care about other elements
        List<ConditionView> conditionViews = views.stream()
                .filter(ConditionView.class::isInstance)
                .map(ConditionView.class::cast)
                .collect(Collectors.toList());

        boolean success = conditionViews.stream().map(ConditionView::validate).reduce(true, (a, b) -> a & b);
        if (!success) {
            // TODO: show notification here
            return;
        }

        for (ConditionView view : conditionViews) {
            Condition condition = view.getCurrentCondition();
            int textFieldValue = view.getTextFieldValue();
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
            view.accept();
        }
        context.setFilterComment(FOOTBALL, commentArea.getText());
    }

    private void saveFilter0(Expression expression) {
        context.addFilter(FOOTBALL, expression);
    }
}
