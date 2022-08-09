package com.dulsara.citylist.ciltylistapp.user.controller;

import com.dulsara.citylist.ciltylistapp.user.model.User;
import com.dulsara.citylist.ciltylistapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody @Valid User user) {
        User createdUser = userService.save(user);
        URI uri = URI.create("/users/" + createdUser.getId());

        return ResponseEntity.created(uri).body(createdUser);
    }
}
