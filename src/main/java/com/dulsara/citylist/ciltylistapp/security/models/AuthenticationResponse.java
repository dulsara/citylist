package com.dulsara.citylist.ciltylistapp.security.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
//
@AllArgsConstructor
@Data
public class AuthenticationResponse implements Serializable {
    private final String jwt;
}
