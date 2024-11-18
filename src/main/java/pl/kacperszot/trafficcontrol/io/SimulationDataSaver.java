package pl.kacperszot.trafficcontrol.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kacperszot.trafficcontrol.simulation.SimulationStep;

import java.io.File;
import java.util.List;

public class SimulationDataSaver {
    private record SimulationStepWrapper(List<SimulationStep> stepStatuses) {
    }
    private static final Logger LOGGER = LogManager.getLogger();


    public void saveSimulationStepsToFile(List<SimulationStep> stepStatuses, String filePath) {
        SimulationStepWrapper wrapper = new SimulationStepWrapper(stepStatuses);
        ObjectMapper mapper = new ObjectMapper();
        LOGGER.info("Writing simulation steps to " + filePath);
        try {
            mapper.writeValue(new File(filePath), wrapper);
        } catch (Exception e) {
            System.out.println("Error: Error while saving steps to file " + filePath + ": " + e.getMessage());
            System.out.println("Usage: traffic-control <input-file> <output-file>);");
            System.exit(1);
        }
    }
}