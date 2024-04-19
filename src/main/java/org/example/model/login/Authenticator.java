package org.example.model.login;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Authenticator {

    // A map that stores the username and a pair of token and timestamp that corresponds to it
    private static Map<String, TokenPair<String, LocalDateTime>> savedTokens = new HashMap<>();

    public static void storeToken(String username, String token) {
        // Stores a given token associated with a username and the current time
        savedTokens.put(username, new TokenPair<>(token, LocalDateTime.now()));
    }

    public static boolean validateUser(String username, String token) {
        TokenPair<String, LocalDateTime> pair = savedTokens.get(username);
        // Checks if the token is stored and equal to the one given
        if (pair != null && Objects.equals(pair.getFirst(), token)) {
            // Checks if the token is not older than 30 minutes
            if (ChronoUnit.MINUTES.between(pair.getSecond(), LocalDateTime.now()) <= 30) {
                return true;
            } else {
                savedTokens.remove(username); // remove the token if it's older than 30 minutes
            }
        }
        return false;
    }
}