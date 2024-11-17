package pl.kacperszot.trafficcontrol.simulation;

import pl.kacperszot.trafficcontrol.model.TrafficState;
import pl.kacperszot.trafficcontrol.model.Vehicle;
import pl.kacperszot.trafficcontrol.model.VehicleStatus;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLightSignal;

import java.util.ArrayList;

public class VehicleManager {

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

        for (var vehicle : vehiclesWaitingOnGreenLight) {
            boolean isClearToGo = true;

            for (var otherVehicle : vehiclesWaitingOnGreenLight) {
                if (vehicle.equals(otherVehicle)) continue;
                if (!vehicle.getRoute().hasRoutePriority(otherVehicle.getRoute())) {
                    isClearToGo = false;
                    vehicle.setStatus(VehicleStatus.BLOCKED);
                    break;
                }
            }
            if (isClearToGo) {
                vehicle.setStatus(VehicleStatus.IN_INTERSECTION);
                leavingVehicles.add(vehicle);
                vehicle.setStatus(VehicleStatus.COMPLETED_CROSSING);
            }

        }

        return new SimulationStep(leavingVehicles);
    }
}