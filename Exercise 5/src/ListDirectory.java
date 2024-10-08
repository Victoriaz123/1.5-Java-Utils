import java.io.IOException;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;


public class ListDirectory implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<String> fileList;

    public ListDirectory() {
        this.fileList = new ArrayList<>();
    }
    public void addFile(String filePath) {
        fileList.add(filePath);
    }
    public List<String> getFileList() {
        return fileList;
    }

    public static void listDirectoryContents(String relativePath,String outputFile){
        Path dir =Paths.get(relativePath.replace("/",File.separator).replace("\\",File.separator));
        ListDirectory listDirectory = new ListDirectory();

        try (Stream<Path> stream = Files.walk(dir)) {
            stream.sorted(Comparator.comparing(Path::toString))
                    .forEach(path -> {
                        try {
                            String type = Files.isDirectory(path) ? "(Directory)" : "(File)";
                            FileTime lastModifiedTime = Files.getLastModifiedTime(path);
                            String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastModifiedTime.toMillis());
                            String fileInfo = type + " " + path.getFileName() + " (Modified: " + formattedDate + ")";
                            listDirectory.addFile(fileInfo);
                        } catch (IOException e) {
                            System.err.println("Error retrieving info for: " + path + " - " + e.getMessage());
                        }
                    });

            Files.write(Paths.get(outputFile), listDirectory.getFileList());

        } catch (IOException e) {
            System.err.println("Error reading directory: " + e.getMessage());
        }
    }


    public static void serializeDirectoryList(ListDirectory listDirectory, String filePath) {
        try (FileOutputStream fileOutputStream  = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream  = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(listDirectory);
            System.out.println("Serialized data is saved in " + filePath);
        } catch (IOException e) {
            System.err.println("Error " + e.getMessage());
        }
    }

    public static ListDirectory deserializeDirectoryList(String filePath) {
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return (ListDirectory) objectInputStream.readObject();
        } catch (IOException e) {
            System.err.println("Error during deserialization: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + e.getMessage());
        }
        return null;
    }

    public static void readTextFile(String filePath) {
        Path path = Paths.get(filePath);

        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
