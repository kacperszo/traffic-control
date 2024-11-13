package pl.kacperszo.trafficcontrol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.kacperszo.trafficcontrol.model.AddVehicleCommand;
import pl.kacperszo.trafficcontrol.model.Command;
import pl.kacperszo.trafficcontrol.model.CommandsWrapper;
import pl.kacperszo.trafficcontrol.model.StepCommand;

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
                
                            }
                
                          ]
                
                        }
                """;
        ObjectMapper mapper = new ObjectMapper();
        CommandsWrapper wrapper = mapper.readValue(inputJSON, CommandsWrapper.class);

        for (Command command : wrapper.commands()) {
            if (command instanceof StepCommand stepCommand) {
                System.out.println("StepCommand");
            } else if (command instanceof AddVehicleCommand addVehicleCommand) {
                System.out.println("AddVehicleCommand: " + addVehicleCommand);
            }
        }
    }
}