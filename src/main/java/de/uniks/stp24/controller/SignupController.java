package de.uniks.stp24.controller;

import de.uniks.stp24.App;
import javafx.event.ActionEvent;
import org.fulib.fx.annotation.controller.Controller;

import javax.inject.Inject;

@Controller
public class SignupController {
    @Inject
    App app;

    @Inject
    public SignupController() {
    }

    public void signUp(ActionEvent actionEvent) {

    }

    public void login(ActionEvent actionEvent) {
        app.show("/login");
    }
}
