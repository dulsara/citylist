package com.dulsara.citylist.ciltylistapp.security.controller;

import com.dulsara.citylist.ciltylistapp.security.jwt.JwtTokenUtil;
import com.dulsara.citylist.ciltylistapp.security.models.AuthenticationRequest;
import com.dulsara.citylist.ciltylistapp.security.models.AuthenticationResponse;
import com.dulsara.citylist.ciltylistapp.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtUtil;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            return ResponseEntity.ok(new AuthenticationResponse(accessToken));

        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


    }
}
