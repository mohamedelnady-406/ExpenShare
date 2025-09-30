package com.example.exception.handler;

import com.example.exception.ConflictException;
import com.example.model.error.ApiError;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
@Produces
public class ConflictExceptionHandler implements ExceptionHandler<ConflictException, HttpResponse<ApiError>> {
    @Override
    public HttpResponse<ApiError> handle(HttpRequest request, ConflictException exception) {
        return HttpResponse
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(exception.getMessage(), "CONFLICT"));
    }
}
