package de.uniks.stp24;

import dagger.Module;
import dagger.Provides;
import de.uniks.stp24.rest.AuthApiService;
import org.mockito.Mockito;

@Module
public class TestModule {
    @Provides
    AuthApiService authApiService() {
        return Mockito.mock(AuthApiService.class);
    }
}
