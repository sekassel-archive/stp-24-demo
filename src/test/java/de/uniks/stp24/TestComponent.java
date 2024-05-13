package de.uniks.stp24;

import dagger.Component;
import de.uniks.stp24.dagger.MainComponent;
import de.uniks.stp24.dagger.MainModule;
import de.uniks.stp24.rest.AuthApiService;
import de.uniks.stp24.rest.UserApiService;
import de.uniks.stp24.service.PrefService;

import javax.inject.Singleton;

@Component(modules = {MainModule.class, TestModule.class})
@Singleton
public interface TestComponent extends MainComponent {
    PrefService prefService();

    AuthApiService authApiService();

    UserApiService userApiService();

    @Component.Builder
    interface Builder extends MainComponent.Builder {
        @Override
        TestComponent build();
    }
}
