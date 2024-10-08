public class Main {
    public static void main(String[] args) {

        String relativePath = "../S1.04.Testing.Exercici1";
        String outputFile = "output.txt";
        ListDirectory.listDirectoryContents(relativePath,outputFile);
        String textFilePath = "countries.txt";
        ListDirectory.readTextFile(textFilePath);
    }
}