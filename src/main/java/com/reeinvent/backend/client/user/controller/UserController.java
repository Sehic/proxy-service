package com.reeinvent.backend.client.user.controller;

import com.reeinvent.backend.client.user.models.User;
import com.reeinvent.backend.client.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.reeinvent.backend.util.EndpointConstants.USER;

@RestController
@RequestMapping(USER)
public class UserController {

    @Autowired
    private UserService userService;

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

}
