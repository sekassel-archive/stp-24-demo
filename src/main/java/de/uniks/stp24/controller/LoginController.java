package de.uniks.stp24.controller;

import de.uniks.stp24.App;
import de.uniks.stp24.dto.LoginDto;
import de.uniks.stp24.service.ErrorService;
import de.uniks.stp24.service.LoginService;
import de.uniks.stp24.service.PrefService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.fulib.fx.annotation.controller.Controller;
import org.fulib.fx.annotation.controller.Resource;
import org.fulib.fx.annotation.controller.Title;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

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
    @Resource
    ResourceBundle resources;
    @Inject
    Provider<ResourceBundle> newResources;
    @Inject
    @Named("game-resources")
    ResourceBundle gameResources;
    @Inject
    PrefService prefService;
    @Inject
    ErrorService errorService;

    @Inject
    public LoginController() {
    }

    public void login() {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        boolean rememberMe = this.rememberMe.isSelected();

        loginService.login(username, password, rememberMe)
            .subscribe(result -> {
                System.out.println(result);
                app.show("/main-menu");
            }, error -> {
                switch (errorService.getStatus(error)) {
                    case 401 -> errorLabel.setText(resources.getString("login.failed"));
                    case 429 -> errorLabel.setText(resources.getString("login.ratelimit"));
                    default -> errorLabel.setText(errorService.getMessage(error));
                }
            });
    }

    public void signup() {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        app.show("/signup", Map.of("username", username, "password", password));
    }

    public void setDe() {
        setLocale(Locale.GERMAN);
    }

    public void setEn() {
        setLocale(Locale.ENGLISH);
    }

    private void setLocale(Locale english) {
        prefService.setLocale(english);
        resources = newResources.get();
        app.refresh();
    }
}
