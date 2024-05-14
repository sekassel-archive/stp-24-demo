package de.uniks.stp24.controller;

import de.uniks.stp24.App;
import de.uniks.stp24.component.UserComponent;
import de.uniks.stp24.model.User;
import de.uniks.stp24.rest.UserApiService;
import de.uniks.stp24.ws.EventListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.fulib.fx.annotation.controller.Controller;
import org.fulib.fx.annotation.controller.SubComponent;
import org.fulib.fx.annotation.controller.Title;
import org.fulib.fx.annotation.event.OnDestroy;
import org.fulib.fx.annotation.event.OnInit;
import org.fulib.fx.annotation.event.OnRender;
import org.fulib.fx.constructs.listview.ComponentListCell;
import org.fulib.fx.controller.Subscriber;

import javax.inject.Inject;
import javax.inject.Provider;

@Controller
@Title("Main Menu")
public class LobbyController {
    @Inject
    App app;
    @Inject
    UserApiService userApiService;
    @Inject
    Subscriber subscriber;
    @Inject
    Provider<UserComponent> userComponentProvider;
    @Inject
    EventListener eventListener;

    @SubComponent
    @Inject
    UserComponent userComponent;

    @FXML
    Button joinButton;
    @FXML
    ListView<User> userList;
    @FXML
    VBox viewBox;

    private final ObservableList<User> users = FXCollections.observableArrayList();

    @Inject
    public LobbyController() {
    }

    @OnInit
    void init() {
        subscriber.subscribe(userApiService.findAll(), this.users::setAll);
        subscriber.subscribe(eventListener.listen("users.*.*", User.class), event -> {
            switch (event.suffix()) {
                case "created" -> users.add(event.data());
                case "updated" -> users.replaceAll(u -> u._id().equals(event.data()._id()) ? event.data() : u);
                case "deleted" -> users.removeIf(u -> u._id().equals(event.data()._id()));
            }
        });
    }

    @OnDestroy
    void destroy() {
        subscriber.dispose();
    }

    @OnRender
    void render() {
        joinButton.disableProperty().bind(userList.getSelectionModel().selectedItemProperty().isNull());
        userList.setItems(users);
        userList.setCellFactory(list -> new ComponentListCell<>(app, userComponentProvider));

        userList.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) {
                viewBox.getChildren().remove(userComponent);
            } else {
                userComponent.setItem(newValue);
                if (userComponent.getParent() == null) {
                    viewBox.getChildren().add(userComponent);
                }
            }
        });

        // Alternative:
        // viewBox.getChildren().add(userComponent);
        // userComponent.visibleProperty().bind(userList.getSelectionModel().selectedItemProperty().isNotNull());
    }

    public void join() {
        System.out.println(userList.getSelectionModel().getSelectedItem());
    }
}
