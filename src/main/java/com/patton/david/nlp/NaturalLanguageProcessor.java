package com.patton.david.nlp;

import com.patton.david.nlp.structures.CharacterToken;
import com.patton.david.nlp.builder.CharacterTokenBuilder;
import com.patton.david.nlp.structures.TextDocument;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/*
 *  A Natural Language Processor.  It is capable of parsing text
 *  and serializing it to disk.
 */
public class NaturalLanguageProcessor {

    private NaturalLanguageProcessorOptions nlpOptions;

    public NaturalLanguageProcessor(NaturalLanguageProcessorOptions nlpOptions) {
        this.nlpOptions = nlpOptions;
    }

    public TextDocument parseFile(String filename) throws IOException {
        return parseFile(filename, new TextDocument());
    }

    public TextDocument parseFile(String filename, TextDocument document) throws IOException  {
        if (filename == null || filename == "") {
            throw new IllegalArgumentException("filename cannot be null or empty");
        }
        if (document == null) {
            document = new TextDocument();
        }
        CharacterTokenBuilder tokenBuilder = new CharacterTokenBuilder();
        try (BufferedInputStream reader = new BufferedInputStream(new FileInputStream(filename))) {
            int c;
            CharacterToken token;
            while ((c = reader.read()) >= 0) {
                token = tokenBuilder.append(new Character((char) c));
                if (token != null) {
                    document.append(token);
                }
            }
            token = tokenBuilder.finalizeToken();
            if (token != null) {
                document.append(token);
            }
            document.finalize();
        }
        return document;
    }
}
