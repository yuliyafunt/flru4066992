package flteam.flru4066992.UI;

import flteam.flru4066992.UI.controllers.FootballController;

import javax.inject.Inject;


public class MainController {

    private final FootballController footballController;

    @Inject
    public MainController(FootballController footballController) {
        this.footballController = footballController;
    }

    public void initialize() {
//        footballController.test();
    }

    private void setup() {
    }
}
