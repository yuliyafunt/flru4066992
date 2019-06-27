package flteam.flru4066992.UI;

import flteam.flru4066992.UI.controllers.FootballController;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

import javax.inject.Inject;


public class MainController {

    private final FootballController footballController;

    @FXML
    private TabPane tabPane;

    @Inject
    public MainController(FootballController footballController) {
        this.footballController = footballController;
    }

    public void initialize() {
        tabPane.widthProperty().addListener((observable, oldValue, newValue) ->
        {
            tabPane.setTabMinWidth(tabPane.getWidth() / 7);
            tabPane.setTabMaxWidth(tabPane.getWidth() / 7);
        });
//        footballController.test();
    }

    private void setup() {
    }
}
