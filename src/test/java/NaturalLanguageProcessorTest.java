import com.patton.david.nlp.NaturalLanguageProcessor;
import com.patton.david.nlp.NaturalLanguageProcessorOptions;
import com.patton.david.nlp.structures.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class NaturalLanguageProcessorTest {

    @Test
    public void parseFile_sizesMatch() throws IOException {
        TextDocument document = parseFile();
        Assert.assertEquals(document.getTokens().size(), 285);
        Assert.assertEquals(document.getSentences().size(), 4);
    }

    @Test
    public void parseFile_noWordOrWhiteSpaceAfterPeriod() throws IOException {
        TextDocument document = parseFile();
        for (Sentence sentence : document.getSentences()) {
            int index = sentence.getEndSentenceIndex();
            CharacterToken token;
            while (!(token = document.getTokens().get(index)).getToken().equals(".")) {
                Assert.assertNotEquals(token.getClass(), Word.class);
                Assert.assertNotEquals(token.getClass(), Whitespace.class);
                index--;
            }
        }
    }

    private TextDocument parseFile() throws IOException {
        NaturalLanguageProcessorOptions nlpOptions = new NaturalLanguageProcessorOptions();
        nlpOptions.setIgnoreCase(false);
        NaturalLanguageProcessor nlp = new NaturalLanguageProcessor(nlpOptions);
        TextDocument document = nlp.parseFile("./data/nlp_data.txt");
        return document;
    }
}
