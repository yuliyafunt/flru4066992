package flteam.flru4066992.UI.controllers;

import flteam.flru4066992.UI.views.ConditionView;
import flteam.flru4066992.core.Context;
import flteam.flru4066992.core.conditions.Expression;
import flteam.flru4066992.core.conditions.sportspecific.Conditions;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static flteam.flru4066992.core.BetType.FOOTBALL;

public abstract class AbstractController {

    @Inject
    private Context context;

    @Nullable
    protected List<ConditionView> validateFilters(VBox conditionContainer) {
        List<ConditionView> conditionViews = conditionContainer.getChildren().stream()
                .filter(ConditionView.class::isInstance)
                .map(ConditionView.class::cast)
                .collect(Collectors.toList());

        boolean success = conditionViews.stream().map(ConditionView::validate).reduce(true, (a, b) -> a & b);
        if (!success) {
            // TODO: show notification here
            return null;
        }
        return conditionViews;
    }

    protected void acceptCondition(ConditionView view, int textFieldValue, Conditions conditionInstance) {
        switch (view.getOperator()) {
            case EQUALS:
                saveFilter0(Expression.eq(conditionInstance, textFieldValue));
                break;
            case GT:
                saveFilter0(Expression.greater(conditionInstance, textFieldValue));
                break;
            case LT:
                saveFilter0(Expression.less(conditionInstance, textFieldValue));
                break;
            case GTE:
                saveFilter0(Expression.greaterOrEq(conditionInstance, textFieldValue));
                break;
            case LTE:
                saveFilter0(Expression.lessOrEq(conditionInstance, textFieldValue));
                break;
        }
        view.accept();
    }

    private void saveFilter0(Expression expression) {
        context.addFilter(FOOTBALL, expression);
    }

    public abstract void delete(String value);
}
