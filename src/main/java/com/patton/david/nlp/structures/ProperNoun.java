package com.patton.david.nlp.structures;

public class ProperNoun {

    private int id;
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    private int startProperNounIndex;
    public int getStartProperNounIndex() {
        return startProperNounIndex;
    }
    public void setStartProperNounIndex(int startProperNounIndex) {
        this.startProperNounIndex = startProperNounIndex;
    }

    private int endProperNoundIndex;
    public int getEndProperNounIndex() {
        return endProperNoundIndex;
    }
    public void setEndProperNoundIndex(int endProperNoundIndex) {
        this.endProperNoundIndex = endProperNoundIndex;
    }

    public int length() {
        return endProperNoundIndex - startProperNounIndex + 1;
    }
}
