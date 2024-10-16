import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.err.println("Please provide the relative path of the directory as a command-line argument.");
            return;
        }
        String relativePath = args[0];
        String outputFile = "output.txt";
        String serFile = "directoryContents.ser";
        String textFilePath = "countries.txt";

        ListDirectory.listDirectoryContents(relativePath,outputFile);

        ListDirectory listDirectory = new ListDirectory();
        listDirectory.addFile("File 1");
        listDirectory.addFile("File 2");

        ListDirectory.serializeDirectoryList(listDirectory, serFile);

        try {
            Optional<ListDirectory> deserializedListDirectoryOpt = ListDirectory.deserializeDirectoryList(serFile);
            ListDirectory deserializedListDirectory = deserializedListDirectoryOpt.orElse(new ListDirectory());
            System.out.println("Deserialized List Directory: " + deserializedListDirectory.getFileList());
        } catch (ListDirectory.DeserializationException e) {
            System.err.println("Failed to deserialize List Directory: " + e.getMessage());
        }

        ListDirectory.readTextFile(textFilePath);
    }
}