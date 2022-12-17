package com.jb.app.security;

import com.jb.app.login.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private ClientType clientType;

}
