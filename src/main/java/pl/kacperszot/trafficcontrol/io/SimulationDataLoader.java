package pl.kacperszot.trafficcontrol.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kacperszot.trafficcontrol.model.command.CommandsWrapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SimulationDataLoader {
    private static final Logger LOGGER = LogManager.getLogger();

    public CommandsWrapper loadCommandsFromFile(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            LOGGER.info("Loading commands from file {}", filePath);
            return mapper.readValue(new File(filePath), CommandsWrapper.class);
        } catch (Exception e) {
            System.out.println("Error: Error while loading commands from file " + filePath + ": " + e.getMessage());
            System.out.println("Usage: traffic-control <input-file> <output-file>);");
            System.exit(1);
        }
        return new CommandsWrapper(List.of());
    }
}
