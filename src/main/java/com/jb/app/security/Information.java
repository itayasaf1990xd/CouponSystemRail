package com.jb.app.security;

import com.jb.app.login.ClientType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Information {

    private int id;
    private String email;
    private LocalDateTime expirationTime;

    @Enumerated(EnumType.STRING)
    private ClientType clientType;

}