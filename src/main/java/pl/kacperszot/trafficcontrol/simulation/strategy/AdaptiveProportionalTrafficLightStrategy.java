package pl.kacperszot.trafficcontrol.simulation.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kacperszot.trafficcontrol.model.intersection.Intersection;
import pl.kacperszot.trafficcontrol.model.road.Road;
import pl.kacperszot.trafficcontrol.model.road.RoadDirection;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLight;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLightSignal;

import java.util.List;

public class AdaptiveProportionalTrafficLightStrategy implements TrafficLightStrategy {
    private static final Logger LOGGER = LogManager.getLogger();

    private final int minGreenTime;
    private final int maxGreenTime;
    private final int transitionTime;
    private final double vehicleWeightFactor;

    private boolean inTransitionState = false;
    private int currentStep = 0;
    private int currentGreenDuration;

    public AdaptiveProportionalTrafficLightStrategy(
            int minGreenTime,
            int maxGreenTime,
            int transitionTime,
            double vehicleWeightFactor
    ) {
        this.minGreenTime = minGreenTime;
        this.maxGreenTime = maxGreenTime;
        this.transitionTime = transitionTime;
        this.vehicleWeightFactor = vehicleWeightFactor;
    }

    @Override
    public void setup(Intersection intersection) {
        // Initially set NORTH-SOUTH to GREEN and EAST-WEST to RED
        for (Road road : intersection.getRoads()) {
            if (road.getDirection() == RoadDirection.NORTH ||
                    road.getDirection() == RoadDirection.SOUTH) {
                road.getTrafficLight().setState(TrafficLightSignal.GREEN);
            } else {
                road.getTrafficLight().setState(TrafficLightSignal.RED);
            }
        }
        // Calculate initial green duration based on waiting vehicles
        // Almost always will be = min time since, we add vehicles after simulation is set up
        currentGreenDuration = calculateGreenDuration(intersection);
    }

    @Override
    public void step(Intersection intersection) {
        List<TrafficLight> trafficLights = getAllTrafficLightsFromIntersection(intersection);

        if (currentStep >= currentGreenDuration && !inTransitionState) {
            // Start transition phase (GREEN -> YELLOW or RED -> RED_YELLOW)
            trafficLights.forEach(TrafficLight::toggleNextState);
            LOGGER.info("Starting transition phase");
            inTransitionState = true;
            currentStep = 0;
        } else if (currentStep >= transitionTime && inTransitionState) {
            // Complete transition (YELLOW -> RED or RED_YELLOW -> GREEN)
            trafficLights.forEach(TrafficLight::toggleNextState);
            LOGGER.info("Completing transition phase");
            inTransitionState = false;
            currentStep = 0;
            // Calculate new green duration for the next phase
            currentGreenDuration = calculateGreenDuration(intersection);
        } else {
            currentStep++;
        }
    }

    private int calculateGreenDuration(Intersection intersection) {
        int totalWaitingVehicles = 0;
        TrafficLightSignal currentState = null;

        // Count waiting vehicles for the direction that's about to get green light
        for (Road road : intersection.getRoads()) {
            TrafficLightSignal state = road.getTrafficLight().getState();
            if (currentState == null) {
                currentState = state;
            }

            if ((state == TrafficLightSignal.RED &&
                    currentState == TrafficLightSignal.RED) ||
                    (state == TrafficLightSignal.GREEN &&
                            currentState == TrafficLightSignal.GREEN)) {
                totalWaitingVehicles += road.getWaitingVehicleCount();
            }
        }


        int additionalTime = (int) (totalWaitingVehicles * vehicleWeightFactor);
        int calculatedDuration = minGreenTime + additionalTime;


        return Math.min(calculatedDuration, maxGreenTime);
    }

    private List<TrafficLight> getAllTrafficLightsFromIntersection(Intersection intersection) {
        return intersection.getRoads().stream()
                .map(Road::getTrafficLight)
                .toList();
    }
}