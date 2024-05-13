package de.uniks.stp24;

import de.uniks.stp24.dto.LoginDto;
import de.uniks.stp24.dto.LoginResult;
import de.uniks.stp24.dto.RefreshDto;
import de.uniks.stp24.model.User;
import de.uniks.stp24.rest.AuthApiService;
import de.uniks.stp24.rest.UserApiService;
import io.reactivex.rxjava3.core.Observable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

public class AppTest extends ControllerTest {
    @BeforeEach
    void setup() {
        final AuthApiService authApiService = testComponent.authApiService();

        Mockito.doReturn(Observable.just(new LoginResult("1", "a", "r")))
            .when(authApiService).login(new LoginDto("alice", "hunter2"));
        Mockito.doReturn(Observable.just(new LoginResult("1", "a", "r")))
            .when(authApiService).refresh(new RefreshDto("r"));

        final UserApiService userApiService = testComponent.userApiService();
        Mockito.doReturn(Observable.just(List.of(new User("1", "alice", null), new User("2", "bob", null))))
            .when(userApiService).findAll();

    }

    @Test
    public void v1() {
        waitForFxEvents();

        assertEquals("Login", stage.getTitle());

        clickOn("#usernameInput");
        write("alice\thunter2\n");
    }

}
