package pl.kacperszot.trafficcontrol.console;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class parses program input arguments
 */
public class ProgramArgumentsParser {

    public record FilePaths(String inputFile, String outputFile) {}
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * parse argument and return paths if successful, exit program otherwise
     * @param args program arguments
     * @return FilePaths
     */
    public FilePaths parse(String[] args) {
        LOGGER.info("Parsing program arguments");
        if (args.length != 2) {
            System.out.println("Error: Missing input or output file.");
            System.out.println("Usage: traffic-control <input-file> <output-file>");
            System.exit(1);
        }

        return new FilePaths(args[0], args[1]);
    }
}
