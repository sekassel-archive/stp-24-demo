package de.uniks.stp24.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.fulib.fx.annotation.controller.Controller;

import javax.inject.Inject;

@Controller
public class LoginController {
    @FXML
    TextField usernameInput;
    @FXML
    PasswordField passwordInput;
    @FXML
    CheckBox rememberMe;
    @FXML
    Text errorLabel;

    @Inject
    public LoginController() {
    }

    public void login() {
    }

    public void signup(ActionEvent actionEvent) {

    }

    public void setDe(ActionEvent actionEvent) {
    }

    public void setEn(ActionEvent actionEvent) {
    }
}
