package de.uniks.stp24.controller;

import de.uniks.stp24.component.UserComponent;
import de.uniks.stp24.model.User;
import de.uniks.stp24.rest.UserApiService;
import de.uniks.stp24.service.ImageCache;
import de.uniks.stp24.ws.Event;
import de.uniks.stp24.ws.EventListener;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.Subject;
import javafx.stage.Stage;
import org.fulib.fx.controller.Subscriber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.util.WaitForAsyncUtils;

import javax.inject.Provider;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LobbyControllerTest extends ControllerTest{
    @Mock
    EventListener eventListener;
    @Mock
    UserApiService userApiService;
    @Spy
    Subscriber subscriber = new Subscriber();
    @Spy
    ImageCache imageCache;
    @Spy
    Provider<UserComponent> userComponentProvider = new Provider<>() {
        @Override
        public UserComponent get() {
            final UserComponent userComponent = new UserComponent();
            userComponent.imageCache = imageCache;
            return userComponent;
        }
    };

    @InjectMocks
    LobbyController lobbyController;

    final Subject<Event<User>> subject = BehaviorSubject.create();

    @Override
    public void start(Stage stage) throws Exception {
        Mockito.doReturn(Observable.just(List.of(
            new User("1", "Alice", null),
            new User("2", "Bob", null)
        ))).when(userApiService).findAll();

        Mockito.doReturn(subject).when(eventListener).listen("users.*.*", User.class);

        super.start(stage);
        app.show(lobbyController);
    }

    @Test
    void test() {
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(2, lobbyController.userList.getItems().size());

        subject.onNext(new Event<>("users.3.created", new User("3", "Charlie", null)));

        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(3, lobbyController.userList.getItems().size());

        subject.onNext(new Event<>("users.2.deleted", new User("2", "Bob", null)));

        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(2, lobbyController.userList.getItems().size());
    }
}
