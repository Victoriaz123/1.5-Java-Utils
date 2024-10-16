public class Main {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.err.println("Please provide the relative path of the directory as a command-line argument.");
            return;
        }
        String relativePath = args[0];
        String outputFile = "output.txt";
        ListDirectory.listDirectoryContents(relativePath,outputFile);
    }
}