package pl.kacperszot.trafficcontrol.simulation.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kacperszot.trafficcontrol.model.intersection.Intersection;
import pl.kacperszot.trafficcontrol.model.road.Road;
import pl.kacperszot.trafficcontrol.model.road.RoadDirection;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLight;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLightSignal;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdaptiveProportionalTrafficLightStrategy implements TrafficLightStrategy {
    private static final Logger LOGGER = LogManager.getLogger();

    private final int minGreenLightTicks;
    private final int maxGreenLightTicks;
    private final int transitionStateTicks;
    private final double vehicleWeightFactor;

    private boolean inTransitionState = false;
    private int currentTick = 0;
    private int currentGreenLightDuration;

    public AdaptiveProportionalTrafficLightStrategy(
            int minGreenLightTicks,
            int maxGreenLightTicks,
            int transitionStateTicks,
            double vehicleWeightFactor
    ) {
        this.minGreenLightTicks = minGreenLightTicks;
        this.maxGreenLightTicks = maxGreenLightTicks;
        this.transitionStateTicks = transitionStateTicks;
        this.vehicleWeightFactor = vehicleWeightFactor;
    }

    @Override
    public void setup(Intersection intersection) {
        // Initially set NORTH-SOUTH to GREEN and EAST-WEST to RED
        for (Road road : intersection.getRoads()) {
            if (road.getDirection() == RoadDirection.NORTH || road.getDirection() == RoadDirection.SOUTH) {
                road.getTrafficLight().setState(TrafficLightSignal.GREEN);
            } else {
                road.getTrafficLight().setState(TrafficLightSignal.RED);
            }
        }
        // Calculate initial green light duration based on waiting vehicles
        calculateNextGreenLightDuration(intersection);
    }

    @Override
    public void step(Intersection intersection) {
        List<TrafficLight> trafficLights = getAllTrafficLightsFromIntersection(intersection);

        if (currentTick == currentGreenLightDuration && !inTransitionState) {
            // Change from GREEN/RED to YELLOW/RED
            trafficLights.forEach(TrafficLight::toggleNextState);
            LOGGER.info("Changing traffic lights to transition state");
            inTransitionState = true;
            currentTick = 0;
        } else if (currentTick == transitionStateTicks && inTransitionState) {
            // Change from YELLOW/RED_YELLOW to RED/GREEN
            trafficLights.forEach(TrafficLight::toggleNextState);
            LOGGER.info("Completing traffic light transition");
            inTransitionState = false;
            currentTick = 0;
            // Calculate next green light duration based on waiting vehicles
            calculateNextGreenLightDuration(intersection);
        } else {
            currentTick++;
        }
    }

    private void calculateNextGreenLightDuration(Intersection intersection) {
        // Get total waiting vehicles for current red direction
        int waitingVehicles = getTotalWaitingVehiclesForCurrentRed(intersection);

        // Calculate green light duration based on waiting vehicles
        int adaptiveDuration = (int) (minGreenLightTicks + (waitingVehicles * vehicleWeightFactor));
        currentGreenLightDuration = Math.min(adaptiveDuration, maxGreenLightTicks);

        LOGGER.info("Calculated green light duration: {} ticks for {} waiting vehicles",
                currentGreenLightDuration, waitingVehicles);
    }

    private int getTotalWaitingVehiclesForCurrentRed(Intersection intersection) {
        return intersection.getRoads().stream()
                .filter(road -> {
                    TrafficLightSignal state = road.getTrafficLight().getState();
                    return state == TrafficLightSignal.RED ||
                            state == TrafficLightSignal.RED_YELLOW;
                })
                .mapToInt(Road::getWaitingVehicleCount)
                .sum();
    }

    private List<TrafficLight> getAllTrafficLightsFromIntersection(Intersection intersection) {
        return intersection.getRoads().stream()
                .map(Road::getTrafficLight)
                .toList();
    }
}
