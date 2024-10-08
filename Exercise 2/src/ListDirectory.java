import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.stream.Stream;


public class ListDirectory {

    public static void listDirectoryContents(String relativePath){
        Path dir =Paths.get(relativePath.replace("/",File.separator).replace("\\",File.separator));
        try (Stream<Path> stream = Files.walk(dir)) {
            stream.sorted(Comparator.comparing(Path::toString))
                    .forEach(path -> {
                        try {
                            String type = Files.isDirectory(path) ? "(Directory)" : "(File)";
                            FileTime lastModifiedTime = Files.getLastModifiedTime(path);
                            String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastModifiedTime.toMillis());
                            System.out.println(type + " " + path.getFileName() + " (Modified: " + formattedDate + ")");
                        } catch (IOException e) {
                            System.err.println("Error retrieving info for: " + path + " - " + e.getMessage());
                        }
                    });
        } catch (IOException e) {
            System.err.println("Error reading directory: " + e.getMessage());
        }
    }
}