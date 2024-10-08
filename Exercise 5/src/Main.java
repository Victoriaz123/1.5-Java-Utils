public class Main {
    public static void main(String[] args) {

        String relativePath = "../S1.04.Testing.Exercici1";
        String outputFile = "output.txt";
        String serFile = "directoryContents.ser";
        String textFilePath = "countries.txt";

        ListDirectory.listDirectoryContents(relativePath,outputFile);

        ListDirectory listDirectory = new ListDirectory();

        listDirectory.addFile("File 1");
        listDirectory.addFile("File 2");
        ListDirectory.serializeDirectoryList(listDirectory, serFile);

        ListDirectory deserializedListDirectory = ListDirectory.deserializeDirectoryList(serFile);
        if (deserializedListDirectory != null) {
            System.out.println("Deserialized List Directory: " + deserializedListDirectory.getFileList());
        } else {
            System.err.println("Failed to deserialize List Directory.");
        }
        ListDirectory.readTextFile(textFilePath);
    }
}