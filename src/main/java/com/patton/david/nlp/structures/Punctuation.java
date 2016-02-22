package com.patton.david.nlp.structures;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Punctuation extends CharacterToken {

    private static final String REGEX_PATTERN = "\\p{Punct}";
    public static final Character PERIOD = new Character('.');

    private Character firstChar;
    private Pattern pattern;
    int i = 0;

    public Punctuation() {
        super();
        this.pattern = Pattern.compile(REGEX_PATTERN);
    }

    public boolean characterTypeMatches(Character c) {
        Matcher m = pattern.matcher(c.toString());
        if (!m.matches()) {
            return false;
        }
        if (firstChar == null) {
            firstChar = c;
        }
        if (firstChar.equals(PERIOD)) {
            return c.equals(PERIOD);
        } else if (!c.equals(PERIOD)){
            return true;
        }
        return false;
    }
}
