package com.patton.david.nlp.builder;

import com.patton.david.nlp.structures.CharacterToken;
import com.patton.david.nlp.structures.Punctuation;
import com.patton.david.nlp.structures.Whitespace;
import com.patton.david.nlp.structures.Word;

import java.util.LinkedList;
import java.util.List;

public class CharacterTokenBuilder {

    private StringBuilder tokenBuilder;
    private CharacterToken currToken;
    private CharacterToken[] possibleTokens;
    private int tokenIndex;

    public CharacterTokenBuilder() {
        this.tokenBuilder = new StringBuilder();
        this.possibleTokens = new CharacterToken[3];
        initializePossibleToken(0);
        initializePossibleToken(1);
        initializePossibleToken(2);
        this.currToken = null;
        this.tokenIndex = 0;
    }

    public CharacterToken append(Character c) {
        if (currToken == null) {
            for (int i = 0; i < possibleTokens.length; i++) {
                if (possibleTokens[i].characterTypeMatches(c)) {
                    currToken = possibleTokens[i];
                    initializePossibleToken(i);
                    break;
                }
            }
        }
        if (currToken.characterTypeMatches(c)) {
            tokenBuilder.append(c);
            return null;
        }
        CharacterToken result = null;
        for (int i = 0; i < possibleTokens.length; i++) {
            if ((result = checkTokenMatch(i, c)) != null) {
                return result;
            }
        }
        throw new RuntimeException(String.format("Could not find appropriate type for %s", c));
    }

    public List<CharacterToken> append(String str) {
        List<CharacterToken> result = null;
        for (Character c : str.toCharArray()) {
            CharacterToken token = append(c);
            if (token != null) {
                if (result == null) {
                    result = new LinkedList<CharacterToken>();
                }
                result.add(token);
            }
        }
        return result;
    }

    public CharacterToken finalizeToken() {
        currToken.setId(tokenIndex++);
        currToken.setToken(tokenBuilder.toString());
        if (currToken.getToken().length() == 0) {
            return null; //Special case when token builder is empty, we do not want to return an empty token
        }
        return currToken;
    }

    private CharacterToken checkTokenMatch(int possibleTokensIndex, Character c) {
        CharacterToken result = null;
        if (possibleTokens[possibleTokensIndex].characterTypeMatches(c)) {
            result = finalizeToken();
            currToken = possibleTokens[possibleTokensIndex];
            initializePossibleToken(possibleTokensIndex);
            tokenBuilder = new StringBuilder();
            tokenBuilder.append(c);
        }
        return result;
    }

    private void initializePossibleToken(int possibleTokenIndex) {
        switch (possibleTokenIndex) {
            case 0:
                possibleTokens[0] = new Word();
                break;
            case 1:
                possibleTokens[1] = new Punctuation();
                break;
            case 2:
                possibleTokens[2] = new Whitespace();
                break;
        }
    }
}
