import java.io.DataOutputStream;
import java.io.IOException;

public class MyDataWriter {

    public DataOutputStream socketOutputStream;

    MyDataWriter(DataOutputStream socketOutputStream) {
        this.socketOutputStream = socketOutputStream;
    }

    public void transmittedDataCheck (long totalCountsBytes, long fileLength) throws IOException {
        if (totalCountsBytes == fileLength) {
            socketOutputStream.writeUTF("All is well");
        } else {
            socketOutputStream.writeUTF("Smth was wrong");
        }
        socketOutputStream.flush();
    }
}
