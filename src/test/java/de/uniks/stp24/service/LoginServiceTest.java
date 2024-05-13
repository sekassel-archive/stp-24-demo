package de.uniks.stp24.service;

import de.uniks.stp24.dto.LoginDto;
import de.uniks.stp24.dto.LoginResult;
import de.uniks.stp24.rest.AuthApiService;
import io.reactivex.rxjava3.core.Observable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    @Mock
    AuthApiService authApiService;
    @Spy
    TokenStorage tokenStorage;
    @Mock
    PrefService prefService;
    @InjectMocks
    LoginService loginService;

    @Test
    void login() {
        final LoginResult expectedResult = new LoginResult("1", "a", "r");
        Mockito.when(authApiService.login(new LoginDto("alice", "hunter2")))
            .thenReturn(Observable.just(expectedResult));

        final Observable<LoginResult> result$ = loginService.login("alice", "hunter2", false);

        // result should be untouched
        final LoginResult result = result$.blockingFirst();
        assertEquals(expectedResult, result);

        // TokenStorage should have the correct data
        assertEquals("1", tokenStorage.getUserId());
        assertEquals("a", tokenStorage.getToken());
    }

    @Test
    void loginRememberMe() {
        final LoginResult expectedResult = new LoginResult("1", "a", "r");
        Mockito.when(authApiService.login(new LoginDto("alice", "hunter2")))
            .thenReturn(Observable.just(expectedResult));

        loginService.login("alice", "hunter2", true)
            .blockingFirst();

        // refresh token should be stored in PrefService
        Mockito.verify(prefService).setRefreshToken("r");
    }
}
