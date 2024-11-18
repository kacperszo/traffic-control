package pl.kacperszot.trafficcontrol.simulation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kacperszot.trafficcontrol.model.TrafficState;
import pl.kacperszot.trafficcontrol.model.Vehicle;
import pl.kacperszot.trafficcontrol.model.VehicleStatus;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLightSignal;

import java.util.ArrayList;

public class VehicleManager {
    private static final Logger LOGGER = LogManager.getLogger();

    public SimulationStep step(TrafficState state) {
        var leavingVehicles = new ArrayList<Vehicle>();
        var vehiclesWaitingOnGreenLight = state
                .getLightStates()
                .keySet()
                .stream()
                .filter(
                        key -> state
                                .getLightStates()
                                .getOrDefault(key, TrafficLightSignal.RED) == TrafficLightSignal.GREEN)
                .filter(key -> state.getWaitingVehicles().containsKey(key))
                .flatMap(key -> state.getWaitingVehicles().get(key).stream())
                .toList();

        //vehicles waiting duo to not having green light
        state
                .getLightStates()
                .keySet()
                .stream()
                .filter(
                        key -> state
                                .getLightStates()
                                .getOrDefault(key, TrafficLightSignal.RED) != TrafficLightSignal.GREEN)
                .filter(key -> state.getWaitingVehicles().containsKey(key))
                .flatMap(key -> state.getWaitingVehicles().get(key).stream())
                .forEach(vehicle -> {
                    vehicle.setStatus(VehicleStatus.WAITING_AT_RED_LIGHT);
                    LOGGER.info("Vehicle is waiting at red light: {}", vehicle);
                });

        for (var vehicle : vehiclesWaitingOnGreenLight) {
            boolean isClearToGo = true;
            vehicle.setStatus(VehicleStatus.WAITING_FOR_CLEARANCE);
            LOGGER.info("Vehicle is waiting for clearance: {}", vehicle);
            for (var otherVehicle : vehiclesWaitingOnGreenLight) {
                if (vehicle.equals(otherVehicle)) continue;
                if (!vehicle.getRoute().hasRoutePriority(otherVehicle.getRoute())) {
                    isClearToGo = false;

                    vehicle.setStatus(VehicleStatus.BLOCKED);
                    LOGGER.info("Vehicle is blocked: {}", vehicle);
                    break;
                }
            }
            if (isClearToGo) {
                vehicle.setStatus(VehicleStatus.IN_INTERSECTION);
                LOGGER.info("Vehicle is in intersection: {}", vehicle);
                leavingVehicles.add(vehicle);
            }

        }

        return new SimulationStep(leavingVehicles);
    }
}