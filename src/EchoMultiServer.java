import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoMultiServer {
    private int port;

    EchoMultiServer(int port) {
        this.port = port;
    }

    public void start() {
        File folder = new File("uploads");
        if (!folder.exists()) {
            folder.mkdir();
        }
        Speedometer speedometer = new Speedometer();
        try(ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("IP = " + InetAddress.getLocalHost());
            while (true)
                new EchoClientHandler(serverSocket.accept(),speedometer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class EchoClientHandler implements Runnable {
        private Socket clientSocket;
        private Thread thread;
        private Speedometer speedometer;
        public Client client;

        public EchoClientHandler(Socket clientSocket, Speedometer spedometer) {
            this.clientSocket = clientSocket;
            this.speedometer = spedometer;
            thread = new Thread(this);
            thread.start();
        }


        public void run() {
            try (DataOutputStream socketOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                 DataInputStream socketInputStream = new DataInputStream(clientSocket.getInputStream())) {

                MyDataReader myDataReader = new MyDataReader(socketInputStream);
                MyDataWriter myDataWriter = new MyDataWriter(socketOutputStream);

                short lengthFileName = myDataReader.readLenghtFileName();

                String fileName = myDataReader.readFileName(lengthFileName);

                long fileLength = myDataReader.readFileLength();

                File receivedFile = (new FileCreator()).createReceivedFile(fileName);

                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(receivedFile))) {
                    client = new Client(System.currentTimeMillis(), fileLength, speedometer);
                    client.addClientToSpeedometer();

                    long totalCountsBytes = myDataReader.startReadFile(fileLength, bos, client);

                    speedometer.printSpeed();
                    client.removeClientFromSpeedometer();
                    //конечная проверка
                    myDataWriter.transmittedDataCheck(totalCountsBytes, fileLength);
                }
            } catch (SocketException e) {
                if(client != null)
                    client.removeClientFromSpeedometer();
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}