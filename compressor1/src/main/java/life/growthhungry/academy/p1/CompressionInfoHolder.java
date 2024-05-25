package life.growthhungry.academy.p1;

import java.util.Map;

public class CompressionInfoHolder {
    private final Map<Short, String> codeToWord;
    private final byte[] codedText;

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
