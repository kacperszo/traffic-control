package pl.kacperszot.trafficcontrol.simulation;

import com.fasterxml.jackson.annotation.JsonValue;
import pl.kacperszot.trafficcontrol.model.Vehicle;

import java.util.List;

public class SimulationStep {

    private final List<Vehicle> leftVehicles;

    public SimulationStep(List<Vehicle> leftVehicles) {
        this.leftVehicles = leftVehicles;
    }


    public List<Vehicle> getLeftVehicles() {
        return leftVehicles;
    }

}