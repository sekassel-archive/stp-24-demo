package de.uniks.stp24;

import dagger.Module;
import dagger.Provides;
import de.uniks.stp24.rest.AuthApiService;
import de.uniks.stp24.rest.UserApiService;
import de.uniks.stp24.service.PrefService;
import org.mockito.Mockito;

import javax.inject.Singleton;
import java.util.Locale;

import static org.mockito.Mockito.mock;

@Module
public class TestModule {
    @Provides
    @Singleton
    PrefService prefService() {
        final PrefService mock = mock(PrefService.class);
        Mockito.doReturn(Locale.ROOT).when(mock).getLocale();
        return mock;
    }

    @Provides
    @Singleton
    AuthApiService authApiService() {
        return mock(AuthApiService.class);
    }

    @Provides
    @Singleton
    UserApiService userApiService() {
        return mock(UserApiService.class);
    }
}
