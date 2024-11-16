package pl.kacperszot.trafficcontrol.simulation;

import pl.kacperszot.trafficcontrol.model.Vehicle;
import pl.kacperszot.trafficcontrol.model.intersection.Intersection;
import pl.kacperszot.trafficcontrol.simulation.strategy.TrafficLightStrategy;

public class SimulationManager {

    private final TrafficLightStrategy trafficLightStrategy;
    private final Intersection intersection;

    public SimulationManager(TrafficLightStrategy trafficLightStrategy, Intersection intersection) {
        this.trafficLightStrategy = trafficLightStrategy;
        this.intersection = intersection;
    }

    void addVehicle(Vehicle vehicle) {

    }

    SimulationStep step() {
        return null;
    }
}
