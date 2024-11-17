package pl.kacperszot.trafficcontrol.console;

public class ProgramArgumentsParser {

    public record FilePaths(String inputFile, String outputFile) {}

    public FilePaths parse(String[] args) {

        if (args.length != 2) {
            System.out.println("Error: Missing input or output file.");
            System.out.println("Usage: traffic-control <input-file> <output-file>);");
            System.exit(1);
        }

        return new FilePaths(args[0], args[1]);
    }
}
