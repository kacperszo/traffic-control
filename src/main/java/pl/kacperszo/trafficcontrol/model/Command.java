package pl.kacperszo.trafficcontrol.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = StepCommand.class, name = "step"),
        @JsonSubTypes.Type(value = AddVehicleCommand.class, name = "addVehicle")
})
public interface Command {
}
