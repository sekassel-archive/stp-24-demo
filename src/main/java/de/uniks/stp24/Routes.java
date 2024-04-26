package de.uniks.stp24;

import de.uniks.stp24.controller.LoginController;
import org.fulib.fx.annotation.Route;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class Routes {
    @Route("login")
    @Inject
    Provider<LoginController> login;

    @Inject
    public Routes() {
    }
}
