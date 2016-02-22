package com.patton.david.nlp.builder;

import com.patton.david.nlp.structures.*;

public class SentenceBuilder {

    private boolean periodFound;
    private TextDocument document;
    private int startTokenIndex, lastTokenIndex, sentenceIndex;

    public SentenceBuilder() {
        this(0, 0);
    }

    public SentenceBuilder(int startTokenIndex, int sentenceIndex) {
        this.periodFound = false;
        this.document = document;
        this.startTokenIndex = startTokenIndex;
        //I subtract 1 because after the first append, you want startTokenIndex == lastTokenIndex
        this.lastTokenIndex = startTokenIndex-1;
        this.sentenceIndex = sentenceIndex;
    }

    public Sentence append(CharacterToken token) {
        Sentence result = null;
        if (periodFound) {
            if (token.getClass() == Whitespace.class) {
                result = finalizeSentence();
            } else if (token.getClass() != Punctuation.class) {
                throw new IllegalStateException("Detected a period followed by something other than punctuation or white space");
            }
        } else {
            periodFound = token.getToken().equals(Punctuation.PERIOD.toString());
        }
        lastTokenIndex++;
        return result;
    }

    public Sentence finalizeSentence() {
        Sentence result = new Sentence();
        result.setStartSentenceIndex(startTokenIndex);
        result.setEndSentenceIndex(lastTokenIndex);
        result.setId(sentenceIndex++);
        startTokenIndex = lastTokenIndex+1;
        periodFound = false;
        return result;
    }
}
