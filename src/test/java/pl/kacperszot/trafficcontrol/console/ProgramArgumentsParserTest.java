package pl.kacperszot.trafficcontrol.console;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProgramArgumentsParserTest {
    private ProgramArgumentsParser parser;

    @BeforeEach
    void setUp() {
        parser = new ProgramArgumentsParser();
    }

    @Test
    void testSuccessfulParsing() {
        String[] validArgs = {"input.txt", "output.txt"};
        ProgramArgumentsParser.FilePaths result = parser.parse(validArgs);

        assertNotNull(result);
        assertEquals("input.txt", result.inputFile());
        assertEquals("output.txt", result.outputFile());
    }
}