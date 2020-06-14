import java.io.*;
import java.net.*;

public class main {
    public static void main(String[] args) throws IOException {
        final int DNS_PORT = 53;
        final String DNS_SERVER_IP = "8.8.8.8";

        sendRequest(args[0], DNS_SERVER_IP, DNS_PORT);
    }

    public static void sendRequest(String host, String ip, int port) throws IOException {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(3000);

            RequestHeader requestHeader = new RequestHeader();
            QuestionHeader questionHeader = new QuestionHeader(host);
            Request request = new Request(requestHeader.getHeader(), questionHeader.getQuestion());

            byte[] req = request.getRequest();
            byte[] response = new byte[1024];

            DatagramPacket requestPacket = new DatagramPacket(req, req.length, InetAddress.getByName(ip), port);
            DatagramPacket responsePacket = new DatagramPacket(response, response.length);

            socket.send(requestPacket);
            socket.receive(responsePacket);

            ParseResponse parser = new ParseResponse(responsePacket.getData(), host);
            parser.parsePacket();
        }
    }
}

