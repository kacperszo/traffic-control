package pl.kacperszot.trafficcontrol.model.command;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Command interface represents single command that can be provided in input for a simulation
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = StepCommand.class, name = "step"),
        @JsonSubTypes.Type(value = AddVehicleCommand.class, name = "addVehicle")
})
public interface Command {
}
