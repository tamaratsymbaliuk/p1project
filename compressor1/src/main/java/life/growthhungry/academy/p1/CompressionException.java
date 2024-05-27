package life.growthhungry.academy.p1;

public class CompressionException extends Exception {
    public CompressionException(String s) {
        super(s);
    }

    public CompressionException(Exception e) {
        super(e);
    }
}
