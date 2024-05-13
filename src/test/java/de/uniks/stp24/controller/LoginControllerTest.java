package de.uniks.stp24.controller;

import de.uniks.stp24.ControllerTest;
import de.uniks.stp24.dto.ErrorResponse;
import de.uniks.stp24.dto.LoginResult;
import de.uniks.stp24.exception.ErrorResponseException;
import de.uniks.stp24.service.ErrorService;
import de.uniks.stp24.service.LoginService;
import io.reactivex.rxjava3.core.Observable;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxAssert;
import org.testfx.matcher.control.TextMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest extends ControllerTest {
    @Spy
    LoginService loginService;
    @Spy
    ErrorService errorService;

    @InjectMocks
    LoginController loginController;

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        app.show(loginController);
    }

    @Test
    void login() {
        doReturn(Observable.just(new LoginResult("1", "a", "r"))).when(loginService).login(any(), any(), anyBoolean());
        doReturn(null).when(app).show("/main-menu");

        // Start:
        // Alice has launched STPellar. She sees the Login screen.
        // She already has an account named "alice123" with password "hunter2".
        assertEquals("Login", stage.getTitle());

        // Action:
        // Alice enters her username "alice123" and her password "hunter2". She clicks "Log In".
        write("alice123\t");
        write("hunter2\t");
        clickOn("Login");

        waitForFxEvents();

        // Result:
        // Alice is now logged in.
        // She sees the main menu.
        verify(loginService, times(1)).login("alice123", "hunter2", false);
        verify(app, times(1)).show("/main-menu");
    }

    @Test
    void failLogin() {
        doReturn(Observable.error(new ErrorResponseException(new ErrorResponse(401, "Forbidden", new String[]{"bad login"}))))
            .when(loginService).login(any(), any(), anyBoolean());

        // Start:
        assertEquals("Login", stage.getTitle());

        // Action:
        write("alice123\t");
        write("hunter3\t");
        clickOn("Login");

        waitForFxEvents();

        // Result:
        assertEquals("Login", stage.getTitle());
        verifyThat("#errorLabel", TextMatchers.hasText("Invalid Username or Password"));
        verify(loginService, times(1)).login("alice123", "hunter3", false);
    }
}
