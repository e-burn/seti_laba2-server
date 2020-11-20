import java.util.Scanner;

public class Main {
    public static final int MIN_PORT_NUMBER = 0;
    public static final int MAX_PORT_NUMBER = 65535;

    public static void main(String[] args) {
        int port;
        try(Scanner in = new Scanner(System.in)) {
            System.out.println("Enter port: ");
            port = 0;
            try {
                port = in.nextInt();
                if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
                    throw new IllegalArgumentException("port is out of range : " + port);
                }
            } catch (RuntimeException e) {
                System.err.println("Exception: " + e);
                port = 6667;
            } finally {
                System.out.println("port = " + port);
            }
        }
        EchoMultiServer Server = new EchoMultiServer(port);
        Server.start();
    }
}
