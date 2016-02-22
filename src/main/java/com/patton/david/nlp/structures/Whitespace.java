package com.patton.david.nlp.structures;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Whitespace extends CharacterToken {

    private static final String REGEX_PATTERN = "\\s";

    private Pattern pattern;

    public Whitespace() {
        super();
        this.pattern = Pattern.compile(REGEX_PATTERN);
    }

    public boolean characterTypeMatches(Character c) {
        Matcher m = pattern.matcher(c.toString());
        return m.matches();
    }
}
