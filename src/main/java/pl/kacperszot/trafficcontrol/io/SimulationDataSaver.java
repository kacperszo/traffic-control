package pl.kacperszot.trafficcontrol.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.kacperszot.trafficcontrol.simulation.SimulationStep;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SimulationDataSaver {
    private record SimulationStepWrapper(List<SimulationStep> stepStatuses) {
    }

    public void saveSimulationStepsToFile(List<SimulationStep> stepStatuses, String filePath) {
        SimulationStepWrapper wrapper = new SimulationStepWrapper(stepStatuses);
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(filePath), wrapper);
        } catch (Exception e) {
            System.out.println("Error: Error while saving steps to file " + filePath + ": " + e.getMessage());
            System.out.println("Usage: traffic-control <input-file> <output-file>);");
            System.exit(1);
        }
    }
}