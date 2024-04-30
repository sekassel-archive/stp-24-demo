package de.uniks.stp24.controller;

import de.uniks.stp24.App;
import de.uniks.stp24.dto.LoginDto;
import de.uniks.stp24.service.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.fulib.fx.annotation.controller.Controller;
import org.fulib.fx.annotation.controller.Title;

import javax.inject.Inject;

@Controller
@Title("Login")
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
    App app;
    @Inject
    LoginService loginService;

    @Inject
    public LoginController() {
    }

    public void login() {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        loginService.login(username, password)
            .subscribe(result -> {
                System.out.println(result);
                app.show("/main-menu");
            });
    }

    public void signup() {
        app.show("/signup");
    }

    public void setDe(ActionEvent actionEvent) {
    }

    public void setEn(ActionEvent actionEvent) {
    }
}
