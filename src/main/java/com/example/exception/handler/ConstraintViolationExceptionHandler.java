package com.example.exception.handler;

import com.example.model.error.ApiError;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.stream.Collectors;

@Singleton
@Produces
@Replaces(io.micronaut.validation.exceptions.ConstraintExceptionHandler.class)
public class ConstraintViolationExceptionHandler
        implements ExceptionHandler<ConstraintViolationException, HttpResponse<ApiError>> {

    @Override
    public HttpResponse<ApiError> handle(HttpRequest request, ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        ApiError apiError = new ApiError(errorMessage, "VALIDATION_ERROR");
        return HttpResponse.badRequest(apiError);
    }
}
