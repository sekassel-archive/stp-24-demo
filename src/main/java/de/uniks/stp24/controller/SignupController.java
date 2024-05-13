package de.uniks.stp24.controller;

import de.uniks.stp24.App;
import de.uniks.stp24.dto.CreateUserDto;
import de.uniks.stp24.rest.UserApiService;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.fulib.fx.annotation.controller.Controller;
import org.fulib.fx.annotation.controller.Resource;
import org.fulib.fx.annotation.event.OnInit;
import org.fulib.fx.annotation.event.OnRender;
import org.fulib.fx.annotation.param.Param;

import javax.inject.Inject;
import java.util.ResourceBundle;

@Controller
public class SignupController {
    public TextField usernameInput;
    public PasswordField passwordInput;
    public PasswordField repeatPasswordInput;
    public Button signupButton;
    public Text errorLabel;

    @Inject
    App app;
    @Inject
    UserApiService userApiService;
    @Inject
    @Resource
    ResourceBundle resources;

    @Param("username")
    String username;
    @Param("password")
    String password;

    @Inject
    public SignupController() {
    }

    @OnRender
    void applyInputs() {
        usernameInput.setText(username);
        passwordInput.setText(password);
    }

    @OnRender
    void ensurePasswordsEqual() {
        final BooleanBinding equalPasswords = passwordInput.textProperty().isEqualTo(repeatPasswordInput.textProperty());
        final BooleanBinding usernameNotEmpty = usernameInput.textProperty().isNotEmpty();

        errorLabel.textProperty().bind(
            /* Bindings.when(equalPasswords.not())
                .then("passwords do not match")
                .otherwise(Bindings.when(usernameNotEmpty.not())
                    .then("missing username")
                    .otherwise(""))

             */
            Bindings.createStringBinding(() -> {
                if (!equalPasswords.get()) {
                    return resources.getString("passwords.not.match");
                } else if (!usernameNotEmpty.get()) {
                    return resources.getString("username.not.set");
                } else {
                    return "";
                }
            }, equalPasswords, usernameNotEmpty)
        );

        signupButton.disableProperty().bind(
            // !(password.equals(repeatPassword) && !username.isEmpty())
            equalPasswords.and(usernameNotEmpty).not()
        );
    }

    public void signUp(ActionEvent actionEvent) {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        userApiService.create(new CreateUserDto(username, password)).subscribe(user -> {
            app.show("/login");
        });
    }

    public void login(ActionEvent actionEvent) {
        app.show("/login");
    }
}
