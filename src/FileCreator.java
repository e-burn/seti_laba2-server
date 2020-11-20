import java.io.File;
import java.io.IOException;

public class FileCreator {

    public File createReceivedFile (String fileName) throws IOException {
        File receivedFile = new File("uploads" + File.separator + fileName);
        if (receivedFile.exists()) {
            receivedFile = new File("uploads" + File.separator + System.currentTimeMillis() + fileName );
        }
        receivedFile.createNewFile();
        return receivedFile;
    }

}
