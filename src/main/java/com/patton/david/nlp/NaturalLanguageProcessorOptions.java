package com.patton.david.nlp;

/*
 *  Options utilized by the Natural Language Processor
 */
public class NaturalLanguageProcessorOptions {

    private boolean ignoreCase;
    public boolean getIgnoreCase() {
        return this.ignoreCase;
    }
    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public NaturalLanguageProcessorOptions() {}
}
