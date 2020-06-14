import java.nio.ByteBuffer;
import java.util.Random;

public class RequestHeader {
    ByteBuffer header = ByteBuffer.allocate(12);

    private void createHeader() {
        byte[] ID = new byte[2];    //Identification bytes are randomly generated.
        new Random().nextBytes(ID);
        header.put(ID);

        header.put((byte) 0x0000); //FLAGS (recursive queries: allowed, set 1 -> 0 for it to be disallowed)
        header.put((byte) 0x1101); //FLAGS (non-authenticated data: unacceptable, set 0 -> 1 for it to be acceptable)

        header.put((byte) 0x0000); // QUESTIONS
        header.put((byte) 0x0001); // QUESTIONS

        /**
         * ONLY USEFUL WHEN PROVIDING ANSWERS.
        header.put((byte) 0x0000); // ANSWER RRs
        header.put((byte) 0x0000); // ANSWER RRs

        header.put((byte) 0x0000); // AUTHORITY RRs
        header.put((byte) 0x0000); // AUTHORITY RRs

        header.put((byte) 0x0000); // ADDITIONAL RRs
        header.put((byte) 0x0000); // ADDITIONAL RRs
        */

    }

    public byte[] getHeader() {
        createHeader();
        return header.array();
    }
}
