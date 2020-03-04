package data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PushDataPackage implements DataPackage {
    private final byte COMMAND = 15;
    private Path path;
    private String fileName;
    private byte[] content;
    private ByteArrayOutputStream data;

    public PushDataPackage(String path) {
        this.path = Paths.get(path);
        fileName = this.path.getFileName().toString();
        try {
            content = Files.readAllBytes(this.path);
            data = new ByteArrayOutputStream();
            data.write(COMMAND);
            //data.write(fileName.length());
            data.write(fileName.getBytes());
            data.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getData() {
        return data.toByteArray();
    }
}
