import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListDirectory {

    public static void listDirectoryContents(String relativePath){
    Path dir =Paths.get(relativePath.replace("/", File.separator).replace("\\",File.separator));
    List<String> fileNames = new ArrayList<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
        for (Path file: stream) {
            fileNames.add(file.getFileName().toString());
        }
    } catch (IOException | DirectoryIteratorException x){
        System.err.println("Error reading directory: " + x);
        return;
        }
        Collections.sort(fileNames);
        for (String fileName : fileNames) {
           System.out.println(fileName);
      }
    }
}
