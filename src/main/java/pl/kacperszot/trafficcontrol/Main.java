package pl.kacperszot.trafficcontrol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.kacperszot.trafficcontrol.model.command.AddVehicleCommand;
import pl.kacperszot.trafficcontrol.model.command.Command;
import pl.kacperszot.trafficcontrol.model.command.CommandsWrapper;
import pl.kacperszot.trafficcontrol.model.command.StepCommand;
import pl.kacperszot.trafficcontrol.model.intersection.CrossroadIntersection;
import pl.kacperszot.trafficcontrol.simulation.SimulationManager;
import pl.kacperszot.trafficcontrol.simulation.VehicleManager;
import pl.kacperszot.trafficcontrol.simulation.strategy.EveryTickStateTurnTrafficLightStrategy;
import pl.kacperszot.trafficcontrol.simulation.strategy.TimeBaseTrafficLightStrategy;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        var inputJSON = """
                        {
                
                          "commands": [
                
                            {
                
                              "type": "addVehicle",
                
                              "vehicleId": "vehicle1",
                
                              "startRoad": "south",
                
                              "endRoad": "north"
                
                            },
                
                            {
                
                              "type": "addVehicle",
                
                              "vehicleId": "vehicle2",
                
                              "startRoad": "north",
                
                              "endRoad": "south"
                
                            },
                
                            {
                
                              "type": "step"
                
                            },
                
                            {
                
                              "type": "step"
                
                            },
                
                            {
                
                              "type": "addVehicle",
                
                              "vehicleId": "vehicle3",
                
                              "startRoad": "west",
                
                              "endRoad": "south"
                
                            },
                
                            {
                
                              "type": "addVehicle",
                
                              "vehicleId": "vehicle4",
                
                              "startRoad": "west",
                
                              "endRoad": "south"
                
                            },
                
                            {
                
                              "type": "step"
                
                            },
                
                            {
                
                              "type": "step"
                
                            },
                            {
                
                              "type": "step"
                
                            },{
                
                              "type": "step"
                
                            },{
                
                              "type": "step"
                
                            },{
                
                              "type": "step"
                
                            },{
                
                              "type": "step"
                
                            },{
                
                              "type": "step"
                
                            }
                
                          ]
                
                        }
                """;
        ObjectMapper mapper = new ObjectMapper();
        CommandsWrapper wrapper = mapper.readValue(inputJSON, CommandsWrapper.class);
        VehicleManager vehicleManager = new VehicleManager();
        SimulationManager simulationManager = new SimulationManager(new CrossroadIntersection(), new TimeBaseTrafficLightStrategy(4, 1), vehicleManager);

        for (Command command : wrapper.commands()) {
            if (command instanceof StepCommand stepCommand) {
                var step = simulationManager.step();
                System.out.println(step);
            } else if (command instanceof AddVehicleCommand addVehicleCommand) {
                simulationManager.addVehicle(addVehicleCommand.getVehicle());
            }
        }
    }
}