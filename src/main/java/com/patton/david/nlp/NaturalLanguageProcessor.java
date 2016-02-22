package com.patton.david.nlp;

import com.patton.david.nlp.structures.*;
import com.patton.david.nlp.builder.CharacterTokenBuilder;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/*
 *  A Natural Language Processor.  It is capable of parsing text
 *  and serializing it to disk.
 */
public class NaturalLanguageProcessor {

    private NaturalLanguageProcessorOptions nlpOptions;

    public NaturalLanguageProcessor(NaturalLanguageProcessorOptions nlpOptions) {
        this.nlpOptions = nlpOptions;
    }

    public TextDocument parseFile(String filename, String dictionaryFilename) throws IOException {
        return parseFile(filename, dictionaryFilename, new TextDocument());
    }

    public TextDocument parseFile(String filename, String dictionaryFilename, TextDocument document) throws IOException  {
        if (filename == null || filename == "") {
            throw new IllegalArgumentException("filename cannot be null or empty");
        }
        if (document == null) {
            document = new TextDocument();
        }
        Dictionary dictionary = null;
        if (dictionaryFilename != null) {
            dictionary = parseDictionaryFile(dictionaryFilename);
        } else {
            dictionary = new Dictionary();
        }
        CharacterTokenBuilder tokenBuilder = new CharacterTokenBuilder();
        try (BufferedInputStream reader = new BufferedInputStream(new FileInputStream(filename))) {
            int c;
            CharacterToken token;
            List<Trie> edges = new LinkedList<Trie>();
            while ((c = reader.read()) >= 0) {
                Character character = new Character((char) c);
                token = tokenBuilder.append(character);
                if (token != null) {
                    document.append(token);
                }
                List<Trie> tmpEdges = new LinkedList<Trie>();
                for (Trie edge : edges) {
                    if (edge.hasNext(character)) {
                        Trie nextEdge = edge.getNext(character);
                        tmpEdges.add(nextEdge);
                    }
                    if (token != null && edge.isEndOfWord()) {
                        if (token == null) {
                            break;
                        }
                        String matchingToken = edge.toString();
                        int matchingTokenLength = matchingToken.length();
                        int endIndex = document.getTokens().size()-1;
                        int startIndex = endIndex+1; //This makes sure that if there is only 1 word, startIndex == endIndex
                        do {
                            matchingTokenLength -= document.getTokens().get(--startIndex).getToken().length();
                        } while (matchingTokenLength > 0);
                        ProperNoun properNoun = new ProperNoun();
                        properNoun.setId(document.getProperNouns().size());
                        properNoun.setStartProperNounIndex(startIndex);
                        properNoun.setEndProperNoundIndex(endIndex);
                        document.appendProperNoun(properNoun);
                    }
                }
                if (dictionary != null && dictionary.getRoot().hasNext(character)) {
                    tmpEdges.add(dictionary.getRoot().getNext(character));
                }
                edges = tmpEdges;
            }
            token = tokenBuilder.finalizeToken();
            if (token != null) {
                document.append(token);
            }
            document.finalize();
        }
        return document;
    }

    private Dictionary parseDictionaryFile(String filename) throws IOException {
        Dictionary result = null;
        if (filename != null) {
            try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(filename))) {
                int c;
                StringBuilder builder = new StringBuilder();
                while ((c = is.read()) >= 0) {
                    Character character = new Character((char) c);
                    if (character.equals('\n')) {
                        if (result == null) {
                            result = new Dictionary();
                        }
                        result.put(builder.toString());
                        builder = new StringBuilder();
                    } else {
                        builder.append(character);
                    }
                }
            }
        }
        return result;
    }
}
