package life.growthhungry.academy.p1;

import java.io.Serializable;
import java.util.Map;

public class CompressionInfoHolder implements Serializable { // whenever you write an object it needs to be serializable
    private final Map<Short, String> codeToWord;// Stores the mapping of codes to words
    private final byte[] codedText;// Stores the compressed text

    public CompressionInfoHolder(Map<Short, String> codeToWord, byte[] codedText) {

        this.codeToWord = codeToWord;
        this.codedText = codedText;
    }

    public Map<Short, String> getCodeToWord() {
        return codeToWord;
    }

    public byte[] getCodedText() {
        return codedText;
    }
}
