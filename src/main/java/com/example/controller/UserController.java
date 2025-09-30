package com.example.controller;

import com.example.model.dto.user.CreateUserRequest;
import com.example.model.entity.UserEntity;
import com.example.model.mapper.UserMapper;
import com.example.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Post("/user")
    public HttpResponse<?> createUser(@Body @Valid CreateUserRequest userRequest){
        System.out.println(userRequest.toString());
        return HttpResponse.created(userService.createUser(userRequest));
    }
}
