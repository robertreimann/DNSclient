import java.nio.ByteBuffer;


public class ParseResponse {
    byte[] response;
    int replyCode;
    ByteBuffer ipv4;
    String IP = "";
    String host;
    int answerRR;

    public ParseResponse(byte[] response, String host) {
        this.response = response;
        this.host = host;
    }

    private void getInfo() {
        replyCode = response[3] & 0x0F;             //checks for errors
        answerRR = Byte.toUnsignedInt(response[7]); //checks for amount of answers.
    }

    public void parsePacket() {
        getInfo();
        switch (replyCode) {
            case 1:
                System.out.println("Format error - The name server was unable to interpret the query.");
                break;
            case 2:
                System.out.println("Server failure - The name server was unable to process this " +
                        "query due to a problem with the name server.");
                break;
            case 3:
                System.out.println("Name Error - Meaningful only for responses from an authoritative name" +
                        "server, this code signifies that the domain name referenced in the query does not exist.");
                break;
            case 4:
                System.out.println("Not Implemented - The name server does not support the requested kind of query.");
                break;
            case 5:
                System.out.println("Refused - The name server refuses to perform the specified operation for policy reasons.");
                break;
            default:
                System.out.println("RECEIVED " + answerRR + " ANSWER(S), DNS QUERY RESULTS:");
                System.out.println(host + ": ");
                for (int i = response.length - 1; i > 0; i--) { //Finds RDATA bytes by finding last non-zero byte
                    if (response[i] != 0) {                     
                        for (int j = 0; j < answerRR; j++) {
                            ipv4 = ByteBuffer.allocate(4);
                            for (int k = i - 3; k <= i; k++) {
                                ipv4.put(response[k]);
                            }
                            i -= 16;

                            for (byte b : ipv4.array()) IP += Byte.toUnsignedInt(b) + ".";
                            System.out.println(IP.substring(0, IP.length() - 1));

                            ipv4.clear();
                            IP = "";
                        }
                        break;
                    }
                }
        }
    }
}

