package com.patton.david.nlp.structures;

import com.patton.david.nlp.builder.SentenceBuilder;

import java.util.ArrayList;
import java.util.List;

public class TextDocument {

    private List<Sentence> sentences;
    public List<Sentence> getSentences() {
        return this.sentences;
    }
    private List<CharacterToken> tokens;
    public List<CharacterToken> getTokens() {
        return this.tokens;
    }
    private SentenceBuilder sentenceBuilder;
    private Word lastWord;

    public TextDocument() {
        this.sentences = new ArrayList<Sentence>();
        this.tokens = new ArrayList<CharacterToken>();
        this.sentenceBuilder = new SentenceBuilder();
        this.lastWord = null;
    }

    public void append(CharacterToken token) {
        tokens.add(token);
        if (token instanceof Word) {
            Word wordToken = (Word) token;
            if (lastWord != null) {
                lastWord.setNextWordId(wordToken.getId());
                wordToken.setPreviousWordId(lastWord.getId());
            }
            lastWord = wordToken;
        }
        Sentence sentence = sentenceBuilder.append(token);
        if (sentence != null) {
            sentences.add(sentence);
        }
    }

    public void finalize() {
        Sentence sentence = sentenceBuilder.finalizeSentence();
        if (sentence.length() > 0) {
            sentences.add(sentence);
        }
    }
}
