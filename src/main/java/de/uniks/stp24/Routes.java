package de.uniks.stp24;

import de.uniks.stp24.controller.LobbyController;
import de.uniks.stp24.controller.LoginController;
import de.uniks.stp24.controller.SignupController;
import org.fulib.fx.annotation.Route;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class Routes {
    @Route("login")
    @Inject
    Provider<LoginController> login;

    @Route("signup")
    @Inject
    Provider<SignupController> signup;

    @Route("main-menu")
    @Inject
    Provider<LobbyController> lobby;

    @Inject
    public Routes() {
    }
}
