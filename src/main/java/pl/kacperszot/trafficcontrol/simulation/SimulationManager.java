package pl.kacperszot.trafficcontrol.simulation;

import pl.kacperszot.trafficcontrol.model.TrafficState;
import pl.kacperszot.trafficcontrol.model.Vehicle;
import pl.kacperszot.trafficcontrol.model.VehicleStatus;
import pl.kacperszot.trafficcontrol.model.intersection.Intersection;
import pl.kacperszot.trafficcontrol.simulation.strategy.TrafficLightStrategy;

public class SimulationManager {
    private final Intersection intersection;
    private final TrafficLightStrategy trafficLightStrategy;
    private final VehicleManager vehicleManager;

    public SimulationManager(Intersection intersection, TrafficLightStrategy trafficLightStrategy) {
        this.intersection = intersection;
        this.trafficLightStrategy = trafficLightStrategy;
        vehicleManager = new VehicleManager();
    }


    public void addVehicle(Vehicle vehicle) {
        intersection.addVehicle(vehicle);
    }

    public SimulationStep step() {
        //new traffic state per step
        TrafficState trafficState = new TrafficState();

        //fill traffic light state
        intersection.getRoads().forEach(trafficState::addLightStateForRoad);

        //fill traffic vehicle state
        intersection.getRoads().stream().flatMap(road -> road.getEntryLanes().stream()).forEach(lane -> {
            var vehicle = lane.peekNextVehicle();
            if (vehicle != null) {
                vehicle.setStatus(VehicleStatus.APPROACHING);
                trafficState.addWaitingVehicle(vehicle);
            }
        });

        //move vehicles
        SimulationStep step = vehicleManager.step(trafficState);

        //remove leaving vehicles from lanes
        //Since leaving vehicle is always first vehicle on entry lane this remove will not take O(n) but O(1),
        //since loop will end always after first element
        //at least for queue implementation of the road line
        step.leftVehicles().forEach(intersection::removeVehicle);
        //tick traffic lights
        trafficLightStrategy.step(intersection);

        return step;
    }
}