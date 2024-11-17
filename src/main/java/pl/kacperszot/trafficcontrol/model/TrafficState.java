package pl.kacperszot.trafficcontrol.model;

import pl.kacperszot.trafficcontrol.model.road.Road;
import pl.kacperszot.trafficcontrol.model.road.RoadDirection;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLightSignal;

import java.util.*;

public class TrafficState {
    private final Map<RoadDirection, List<Vehicle>> waitingVehicles;
    private final Map<RoadDirection, TrafficLightSignal> lightStates;

    public TrafficState() {
        waitingVehicles = new HashMap<>();
        lightStates = new HashMap<>();
    }

    public Map<RoadDirection, List<Vehicle>> getWaitingVehicles() {
        return waitingVehicles;
    }

    public Map<RoadDirection, TrafficLightSignal> getLightStates() {
        return lightStates;
    }

    public void addWaitingVehicle(Vehicle vehicle) {
        List<Vehicle> vehicles = waitingVehicles.getOrDefault(vehicle.getRoute().getStart(), new ArrayList<Vehicle>());
        vehicles.add(vehicle);
        waitingVehicles.put(vehicle.getRoute().getStart(), vehicles);
    }

    public void addLightStateForRoad(Road road) {
        lightStates.put(road.getDirection(), road.getTrafficLight().getState());
    }
}