package com.jb.app.security;

import com.jb.app.exceptions.CouponSysException;
import com.jb.app.exceptions.ExceptionType;
import com.jb.app.login.ClientType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenManager {

    private final Map<UUID, Information> tokens;

    public UUID addToken(Information information) {
        deleteToken(information.getId());
        UUID token = UUID.randomUUID();
        tokens.put(token, information);
        return token;
    }

    public void deleteToken(int id) {
        tokens.entrySet().removeIf((token) -> token.getValue().getId() == id);
    }

    /**
     * this makes sure that the client type matches the requirements for the action before returning the id, this is private and will be called from other, public getter methods. allows for simple use of getters.
     * @param token uuid that we got from the user request
     * @param clientType required
     * @return id of the user, after making sure it's the type we want
     * @throws CouponSysException
     */
    private int getUserIdPerType(UUID token, ClientType clientType) throws CouponSysException {
        Information information = tokens.get(token);
        if (information == null) throw new CouponSysException(ExceptionType.UNAUTHORIZED_TOKEN);
        System.out.println(information.getClientType());
        if (information.getClientType() != clientType) throw new CouponSysException(ExceptionType.UNAUTHORIZED_ACTION);
        return information.getId();
    }

    public int getUserId(UUID token) throws CouponSysException {
        Information information = tokens.get(token);
        if (information == null) throw new CouponSysException(ExceptionType.UNAUTHORIZED_TOKEN);

        return information.getId();
    }

    public int getCustomerId(UUID token) throws CouponSysException {
        return getUserIdPerType(token, ClientType.CUSTOMER);
    }

    public int getCompanyId(UUID token) throws CouponSysException {
        return getUserIdPerType(token, ClientType.COMPANY);
    }

    public ClientType getUserClientType(UUID token) throws CouponSysException {
        Information information = tokens.get(token);
        if (information == null) throw new CouponSysException(ExceptionType.UNAUTHORIZED_TOKEN);

        return information.getClientType();
    }

    public ClientType getClientType(UUID token) throws CouponSysException {
        Information information = tokens.get(token);
        if (information == null) throw new CouponSysException(ExceptionType.UNAUTHORIZED_TOKEN);

        return information.getClientType();
    }

    @Scheduled(fixedRate = 1000*60)
    public void deleteExpiredTokens() {
        tokens.entrySet().removeIf((token)->token.getValue().getExpirationTime().isBefore(LocalDateTime.now()));
    }

    public boolean isAuthorized(UUID token, ClientType clientType) throws CouponSysException {

        Information information = tokens.get(token);
        if (information == null) throw new CouponSysException(ExceptionType.UNAUTHORIZED_TOKEN);

        return tokens.get(token).getClientType().equals(clientType);
    }

}
