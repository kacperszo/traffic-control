package pl.kacperszot.trafficcontrol.simulation;

import pl.kacperszot.trafficcontrol.model.TrafficState;
import pl.kacperszot.trafficcontrol.model.Vehicle;
import pl.kacperszot.trafficcontrol.model.VehicleStatus;
import pl.kacperszot.trafficcontrol.model.intersection.Intersection;
import pl.kacperszot.trafficcontrol.model.road.RoadLane;
import pl.kacperszot.trafficcontrol.simulation.strategy.TrafficLightStrategy;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.List;

public class SimulationManager {
    private final Intersection intersection;
    private final TrafficLightStrategy trafficLightStrategy;
    private final VehicleManager vehicleManager;
    private static final Logger LOGGER = LogManager.getLogger();

    public SimulationManager(Intersection intersection, TrafficLightStrategy trafficLightStrategy, VehicleManager vehicleManager) {
        this.intersection = intersection;
        this.trafficLightStrategy = trafficLightStrategy;
        this.vehicleManager = vehicleManager;
        LOGGER.info("Starting Simulation");
    }


    public void addVehicle(Vehicle vehicle) {
        LOGGER.info("Adding vehicle: " + vehicle);
        intersection.addVehicle(vehicle);
    }

    public SimulationStep step() {
        LOGGER.info("Simulation step");

        //new traffic state per step
        TrafficState trafficState = new TrafficState();

        //count waiting on lanes

        //fill traffic light state
        intersection.getRoads().forEach(trafficState::addLightStateForRoad);

        //fill traffic vehicle state

        intersection.getRoads().stream().flatMap(road -> road.getEntryLanes().stream()).forEach(lane -> {
            var vehicle = lane.peekNextVehicle();
            if (vehicle != null) {
                vehicle.setStatus(VehicleStatus.APPROACHING);
                trafficState.addWaitingVehicle(vehicle);
                LOGGER.info("Vehicle approaching: " + vehicle);
            }
        });

        //move vehicles
        SimulationStep step = vehicleManager.step(trafficState);

        //remove leaving vehicles from lanes
        step.getLeftVehicles().forEach(intersection::removeVehicle);
        //tick traffic lights
        trafficLightStrategy.step(intersection);

        return step;
    }
}