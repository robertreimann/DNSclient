import java.nio.ByteBuffer;

public class QuestionHeader {
    String host;
    ByteBuffer question;

    public QuestionHeader(String host) {
        this.host = host;
    }

    private void createQuestion() {
        question = ByteBuffer.allocate(host.length()+6);
        String[] s = host.split("\\.");

        for (int i = 0; i < s.length; i++) {
            question.put((byte) s[i].length());
            for (int j = 0; j < s[i].length(); j++) {
                question.put((byte) (int) s[i].charAt(j));
            }
        }

        question.put((byte) 0x0000); //terminates QNAME

        question.put((byte) 0x0000); //Type A
        question.put((byte) 0x0001); //Type A

        question.put((byte) 0x0000); //Class IN
        question.put((byte) 0x0001); //Class IN
    }

    public byte[] getQuestion() {
        createQuestion();
        return question.array();
    }
}
