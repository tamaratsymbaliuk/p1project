package life.growthhungry.academy.p1;

import java.io.*;

public class Decompressor {
    public static void main(String[] args) throws IOException, ClassNotFoundException, CompressionException {
        String compressedFile = "data/shakespeare.txt.sc";
        String decompressedFile = compressedFile + ".txt";

        // now read the file that we just wrote
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(compressedFile));
        Object inputObject = inputStream.readObject();
        if (!(inputObject instanceof CompressionInfoHolder inputHolder)) {
            throw new CompressionException("Unexpected type receieved. Program expects " + CompressionInfoHolder.class.getCanonicalName());
        }

        // write text into file
        BufferedWriter decompressingWriter = new BufferedWriter(new FileWriter(decompressedFile));
        // decode it
        byte[] codedBytes = inputHolder.getCodedText();
        for (int i = 0; i < codedBytes.length; i += 2) { // every iteration we read 2 bytes at a time
            short high = codedBytes[i];
            short low = codedBytes[i + 1];
            short code = (short) ((high << 8) + low);

            String word = inputHolder.getCodeToWord().get(code);
            // writing words into file word by word
            if (word != null) decompressingWriter.write(word);
        }
        decompressingWriter.flush();
        decompressingWriter.close();

        System.out.println("Compressed file had: " + (codedBytes.length / 2) + " words.");
        System.out.println("Compressed file has: " + inputHolder.getCodeToWord().size() + " unique words.");

    }
}
