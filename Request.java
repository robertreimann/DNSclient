import java.nio.ByteBuffer;

public class Request {
    ByteBuffer request;
    byte[] header;
    byte[] question;

    public Request(byte[] header, byte[] question) {
        this.header = header;
        this.question = question;
    }

    private void createRequest() {
        request = ByteBuffer.allocate(header.length+question.length);
        request.put(header);
        request.put(question);
    }

    public byte[] getRequest() {
        createRequest();
        return request.array();
    }
}
