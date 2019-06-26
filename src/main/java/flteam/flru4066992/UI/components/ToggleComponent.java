package flteam.flru4066992.UI.components;

import flteam.flru4066992.core.conditions.Operator;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ToggleComponent {

    private final HBox box = new HBox();
    private final List<ToggleButton> toggleButtons = new ArrayList<>();

    public ToggleComponent() {
        for (Operator operator : Operator.values()) {
            ToggleButton btn = new ToggleButton(operator.value);
            btn.setOnMouseClicked(event -> handleToggle(btn));
            toggleButtons.add(btn);
        }
        box.getChildren().addAll(toggleButtons);
    }

    private void handleToggle(ToggleButton selected) {
        toggleButtons.forEach(b -> b.setSelected(false));
        selected.setSelected(true);
    }

    public Node getView() {
        return box;
    }

    @Nullable
    public Operator getOperator() {
        ToggleButton btn = toggleButtons.stream()
                .filter(ToggleButton::isSelected)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("One button must be toggled"));
        return Operator.find(btn.getText());
    }
}
