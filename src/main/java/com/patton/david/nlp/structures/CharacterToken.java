package com.patton.david.nlp.structures;

public abstract class CharacterToken {

    private int id;
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    private String token;
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public CharacterToken() {}

    public abstract boolean characterTypeMatches(Character c);

    public String toString() {
        return getToken();
    }
}
