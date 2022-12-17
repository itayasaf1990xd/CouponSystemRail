package com.jb.app.security;

import com.jb.app.login.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Optional;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private UUID token;
    private String email;

    private int id;
    private String name;

    @Enumerated(EnumType.STRING)
    private ClientType clientType;
}
