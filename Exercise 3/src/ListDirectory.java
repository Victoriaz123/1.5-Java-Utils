import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class ListDirectory {

    public static void listDirectoryContents(String relativePath,String outputFile){
        Path dir =Paths.get(relativePath.replace("/",File.separator).replace("\\",File.separator));
        List<String> output = new ArrayList<>();

        if (!Files.exists(dir) || !Files.isDirectory(dir)) {
            System.err.println("Provided path is not a valid directory: " + relativePath);
            return;
        }

        try (Stream<Path> stream = Files.walk(dir)) {
            stream.sorted(Comparator.comparing(Path::toString))
                    .forEach(path -> {
                        try {
                            String type = Files.isDirectory(path) ? "(Directory)" : "(File)";
                            FileTime lastModifiedTime = Files.getLastModifiedTime(path);
                            String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastModifiedTime.toMillis());
                            output.add(type + " " + path.toAbsolutePath() + " (Modified: " + formattedDate + ")");
                        } catch (IOException e) {
                            System.err.println("Error retrieving info for: " + path + " - " + e.getMessage());
                        }
                    });

            Files.write(Paths.get(outputFile), output);
            System.out.println("Results written to: " + outputFile);
        } catch (IOException e) {
            System.err.println("Error reading directory or writing to file: " + e.getMessage());
        }
    }
}