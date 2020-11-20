import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class MyDataReader {

    public DataInputStream socketInputStream;

    MyDataReader(DataInputStream socketInputStream) {
        this.socketInputStream = socketInputStream;
    }

    public short readLenghtFileName() throws IOException {
        return socketInputStream.readShort();
    }

    public String readFileName(short lengthFileName) throws IOException {
        byte[] byteString = new byte[lengthFileName];
        socketInputStream.readFully(byteString);
        return (new String(byteString));
    }

    public long readFileLength() throws IOException {
        return socketInputStream.readLong();
    }

    public long startReadFile(long fileLength, BufferedOutputStream bos, Client client) throws IOException {
        int bytesRead = 0;
        long totalCountsBytes = 0;
        byte[] bytearray = new byte[1024];

        while (totalCountsBytes < fileLength) {
            bytesRead = socketInputStream.read(bytearray);
            bos.write(bytearray, 0, bytesRead);
            totalCountsBytes += bytesRead;
            client.bytesRead = totalCountsBytes;
        }
        bos.flush();
        return totalCountsBytes;
    }
}
