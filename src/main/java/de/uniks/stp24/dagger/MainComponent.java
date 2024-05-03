package de.uniks.stp24.dagger;

import dagger.BindsInstance;
import dagger.Component;
import de.uniks.stp24.App;
import de.uniks.stp24.Routes;
import de.uniks.stp24.service.ImageCache;
import de.uniks.stp24.service.LoginService;

import javax.inject.Singleton;

@Component(modules = {
    MainModule.class,
    HttpModule.class,
    // TODO add additional modules
})
@Singleton
public interface MainComponent {

    Routes routes();

    LoginService loginService();

    ImageCache imageCache();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder mainApp(App app);

        MainComponent build();
    }

}
