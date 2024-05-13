package de.uniks.stp24;

import dagger.Module;
import dagger.Provides;
import de.uniks.stp24.rest.AuthApiService;
import de.uniks.stp24.rest.UserApiService;
import de.uniks.stp24.service.PrefService;

import static org.mockito.Mockito.mock;

@Module
public class TestModule {
    @Provides
    PrefService prefService() {
        return mock(PrefService.class);
    }

    @Provides
    AuthApiService authApiService() {
        return mock(AuthApiService.class);
    }

    @Provides
    UserApiService userApiService() {
        return mock(UserApiService.class);
    }
}
