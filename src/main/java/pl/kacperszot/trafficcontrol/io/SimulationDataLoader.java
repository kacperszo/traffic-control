package pl.kacperszot.trafficcontrol.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.kacperszot.trafficcontrol.model.command.CommandsWrapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SimulationDataLoader {
    public CommandsWrapper loadCommandsFromFile(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filePath), CommandsWrapper.class);
        } catch (Exception e) {
            System.out.println("Error: Error while loading commands from file " + filePath + ": " + e.getMessage());
            System.out.println("Usage: traffic-control <input-file> <output-file>);");
            System.exit(1);
        }
        return new CommandsWrapper(List.of());
    }
}
