package com.patton.david.nlp.structures;

public class Sentence {

    private int id;
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    private int startSentenceIndex;
    public int getStartSentenceIndex() {
        return startSentenceIndex;
    }
    public void setStartSentenceIndex(int startSentenceIndex) {
        this.startSentenceIndex = startSentenceIndex;
    }

    private int endSentenceIndex;
    public int getEndSentenceIndex() {
        return endSentenceIndex;
    }
    public void setEndSentenceIndex(int endSentenceIndex) {
        this.endSentenceIndex = endSentenceIndex;
    }

    public int length() {
        return endSentenceIndex - startSentenceIndex + 1;
    }
}
