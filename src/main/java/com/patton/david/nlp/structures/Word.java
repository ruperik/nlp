package com.patton.david.nlp.structures;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Word extends CharacterToken {

    private static final String REGEX_PATTERN = "\\w";

    private Pattern pattern;

    private int previousWordId;
    public int getPreviousWordId() {
        return this.previousWordId;
    }
    public void setPreviousWordId(int previousWordId) {
        this.previousWordId = previousWordId;
    }

    private int nextWordId;
    public int getNextWordId() {
        return this.nextWordId;
    }
    public void setNextWordId(int nextWordId) {
        this.nextWordId = nextWordId;
    }

    public Word() {
        super();
        this.pattern = Pattern.compile(REGEX_PATTERN);
    }

    public boolean characterTypeMatches(Character c) {
        Matcher matcher = pattern.matcher(c.toString());
        return matcher.matches();
    }
}
