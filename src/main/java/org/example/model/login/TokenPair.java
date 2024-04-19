package org.example.model.login;

public class TokenPair<F, S> {
    private F first;
    private S second;

    public TokenPair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }
}