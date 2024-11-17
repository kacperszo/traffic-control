package pl.kacperszot.trafficcontrol.simulation;

import com.fasterxml.jackson.annotation.JsonValue;
import pl.kacperszot.trafficcontrol.model.Vehicle;

import java.util.List;

public record SimulationStep(List<Vehicle> leftVehicles) {


    @JsonValue
    public List<String> getJsonValue() {
        return leftVehicles.stream().map(Vehicle::getId).toList();
    }
}
