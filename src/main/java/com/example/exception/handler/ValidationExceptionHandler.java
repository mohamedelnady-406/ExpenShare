package com.example.exception.handler;

import com.example.exception.ValidationException;
import com.example.model.error.ApiError;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
@Produces
public class ValidationExceptionHandler implements ExceptionHandler<ValidationException, HttpResponse<ApiError>> {
    @Override
    public HttpResponse<ApiError> handle(HttpRequest request, ValidationException exception) {
        return HttpResponse.badRequest(new ApiError(exception.getMessage(), "VALIDATION_ERROR"));
    }
}