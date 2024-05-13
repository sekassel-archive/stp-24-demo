package de.uniks.stp24.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.uniks.stp24.dto.ErrorResponse;
import de.uniks.stp24.exception.ErrorResponseException;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

import javax.inject.Inject;
import java.io.IOException;

public class ErrorService {
    @Inject
    ObjectMapper objectMapper;

    @Inject
    public ErrorService() {
    }

    public int getStatus(Throwable ex) {
        return switch (ex) {
            case HttpException httpEx -> httpEx.code();
            case ErrorResponseException errorResponseEx -> errorResponseEx.getResponse().statusCode();
            default -> -1;
        };
    }

    public String getMessage(Throwable ex) {
        return switch (ex) {
            case HttpException httpEx -> String.join("\n", getErrorResponse(httpEx).message());
            case ErrorResponseException errorResponseException -> errorResponseException.getMessage();
            default -> ex.getLocalizedMessage();
        };
    }

    public ErrorResponse getErrorResponse(HttpException httpEx) {
        try (ResponseBody body = httpEx.response().errorBody()){
            return objectMapper.readValue(body.string(), ErrorResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
