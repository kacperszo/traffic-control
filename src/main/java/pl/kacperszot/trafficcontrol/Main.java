package pl.kacperszot.trafficcontrol;

import pl.kacperszot.trafficcontrol.console.ProgramArgumentsParser;
import pl.kacperszot.trafficcontrol.io.SimulationDataLoader;
import pl.kacperszot.trafficcontrol.io.SimulationDataSaver;
import pl.kacperszot.trafficcontrol.model.command.AddVehicleCommand;
import pl.kacperszot.trafficcontrol.model.command.Command;
import pl.kacperszot.trafficcontrol.model.command.CommandsWrapper;
import pl.kacperszot.trafficcontrol.model.command.StepCommand;
import pl.kacperszot.trafficcontrol.model.intersection.CrossroadIntersection;
import pl.kacperszot.trafficcontrol.simulation.SimulationManager;
import pl.kacperszot.trafficcontrol.simulation.SimulationStep;
import pl.kacperszot.trafficcontrol.simulation.VehicleManager;
import pl.kacperszot.trafficcontrol.simulation.strategy.AdaptiveProportionalTrafficLightStrategy;
import pl.kacperszot.trafficcontrol.simulation.strategy.TimeBaseTrafficLightStrategy;
import pl.kacperszot.trafficcontrol.simulation.strategy.TrafficLightStrategy;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ProgramArgumentsParser parser = new ProgramArgumentsParser();
        ProgramArgumentsParser.FilePaths filePaths = parser.parse(args);
        SimulationDataLoader simulationDataLoader = new SimulationDataLoader();
        SimulationDataSaver simulationDataSaver = new SimulationDataSaver();
        CommandsWrapper wrapper = simulationDataLoader.loadCommandsFromFile(filePaths.inputFile());
        VehicleManager vehicleManager = new VehicleManager();

        TrafficLightStrategy strategy = new AdaptiveProportionalTrafficLightStrategy(
                1,
                10,
                1,
                1.0
        );

        SimulationManager simulationManager = new SimulationManager(new CrossroadIntersection(), strategy, vehicleManager);

        var steps = new ArrayList<SimulationStep>();

        for (Command command : wrapper.commands()) {
            if (command instanceof StepCommand stepCommand) {
                var step = simulationManager.step();
                steps.add(step);
            } else if (command instanceof AddVehicleCommand addVehicleCommand) {
                simulationManager.addVehicle(addVehicleCommand.getVehicle());
            }
        }
        simulationDataSaver.saveSimulationStepsToFile(steps, filePaths.outputFile());
    }
}