package de.uniks.stp24.rest;

import de.uniks.stp24.dto.CreateUserDto;
import de.uniks.stp24.model.User;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.*;

import java.util.List;

public interface UserApiService {
    @POST("users")
    Observable<User> create(@Body CreateUserDto dto);

    @GET("users")
    Observable<List<User>> findAll();

    @GET("users")
    Observable<List<User>> findAll(@Query("ids") List<String> ids);

    @GET("users/{id}")
    Observable<User> findOne(@Path("id") String id);
}
