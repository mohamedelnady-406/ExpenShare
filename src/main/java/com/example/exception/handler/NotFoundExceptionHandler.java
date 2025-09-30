package com.example.exception.handler;

import com.example.exception.NotFoundException;
import com.example.model.error.ApiError;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Singleton
@Produces
public class NotFoundExceptionHandler implements ExceptionHandler<NotFoundException, HttpResponse<ApiError>> {

    @Override
    public HttpResponse<ApiError> handle(HttpRequest request, NotFoundException exception) {
        ApiError apiError = new ApiError(exception.getMessage(), "NOT_FOUND");
        return HttpResponse.notFound(apiError);
    }
}