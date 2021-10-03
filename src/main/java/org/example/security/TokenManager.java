package org.example.security;

import java.util.Date;

/**
 * by Iskortsev S.V.
 */
//создание и проверка токенов
public class TokenManager {

    private final String secretKey;

    public TokenManager(String secretKey) {
        this.secretKey = secretKey;
    }

    //создание токена
    public String createToken(TokenPayload payload) {
        String mixedPayload = createMixedTokenPayload(payload);
        String sigbature = createSignature(mixedPayload); // цифоровая подпись
        return String.format("%s#%s", mixedPayload, sigbature);
    }

    // объединяет все поля в строку с разделителем
    private String createMixedTokenPayload(TokenPayload payload) {
        String timeOfCreation = String.valueOf(payload.getTimeOfCreation().getTime());
        String id = String.valueOf(payload.getUserId());
        String email = payload.getEmail();

        return String.format("%s#%s#%s", id, email, timeOfCreation);
    }

    // создание цифровой подписи (без шифрования)
    private String createSignature(String mixedPayload) {
        return "" + mixedPayload.charAt(0)
                + mixedPayload.charAt(2)
                + secretKey.charAt(0)
                + secretKey.charAt(2)
                + secretKey.charAt(5)
                + mixedPayload.charAt(mixedPayload.length() - 1);
    }

    //проверка токена
    public boolean verifyToken(String token) {
        TokenPayload payload = extractPayload(token);
        String trustedToken = createToken(payload);
        return token.equals(trustedToken);
    }

    //извлечение
    public TokenPayload extractPayload(String token) {
        String[] tokenParts = token.split("#");
        Long id = Long.valueOf(tokenParts[0]);
        String email = tokenParts[1];
        Date timeOfCreation = new Date(Long.parseLong(tokenParts[2]));

        return new TokenPayload(id, email, timeOfCreation);
    }

}
