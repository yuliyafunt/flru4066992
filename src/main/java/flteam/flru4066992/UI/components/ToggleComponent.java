package flteam.flru4066992.UI.components;

import flteam.flru4066992.core.conditions.Operator;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ToggleComponent extends HBox {

    private final List<ToggleButton> toggleButtons = new ArrayList<>();

    public ToggleComponent() {
        for (Operator operator : Operator.values()) {
            ToggleButton btn = new ToggleButton(operator.value);
            btn.setOnMouseClicked(event -> handleToggle(btn));
            toggleButtons.add(btn);
        }
        getChildren().addAll(toggleButtons);
    }

    private void handleToggle(ToggleButton selected) {
        toggleButtons.forEach(b -> b.setSelected(false));
        selected.setSelected(true);
    }

    @Nullable
    public Operator getOperator() {
        ToggleButton btn = toggleButtons.stream()
                .filter(ToggleButton::isSelected)
                .findAny()
                .orElse(null);
        return btn == null ? null : Operator.find(btn.getText());
    }
}
