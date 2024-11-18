package pl.kacperszot.trafficcontrol.simulation.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kacperszot.trafficcontrol.model.intersection.Intersection;
import pl.kacperszot.trafficcontrol.model.road.Road;
import pl.kacperszot.trafficcontrol.model.road.RoadDirection;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLight;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLightSignal;

import java.util.List;

public class TimeBaseTrafficLightStrategy implements TrafficLightStrategy {
    private final int ticksPerStableState;
    private final int ticksPerTransitionState;
    private boolean inTransitionState = false;

    private int step = 0;

    private static final Logger LOGGER = LogManager.getLogger();


    public TimeBaseTrafficLightStrategy(int ticksPerStableState, int ticksPerTransitionState) {
        this.ticksPerStableState = ticksPerStableState;
        this.ticksPerTransitionState = ticksPerTransitionState;
    }

    @Override
    public void setup(Intersection intersection) {
        //make NORTH-SOUTH GREEN
        //and EAST-WEST RED
        for (Road road : intersection.getRoads()) {
            if (road.getDirection() == RoadDirection.NORTH || road.getDirection() == RoadDirection.SOUTH) {
                road.getTrafficLight().setState(TrafficLightSignal.GREEN);
            } else {
                road.getTrafficLight().setState(TrafficLightSignal.RED);
            }
        }
    }

    @Override
    public void step(Intersection intersection) {
        List<TrafficLight> trafficLights = getAllTrafficLightsFromIntersection(intersection);

        if (step == ticksPerStableState && !inTransitionState) {
            trafficLights.forEach(TrafficLight::toggleNextState);
            LOGGER.info("Changing TrafficLights");
            inTransitionState = true;
            step = 0;
        } else if (step == ticksPerTransitionState && inTransitionState) {
            trafficLights.forEach(TrafficLight::toggleNextState);
            LOGGER.info("Changing TrafficLights");
            inTransitionState = false;
            step = 0;
        } else {
            step += 1;
        }

    }

    private List<TrafficLight> getAllTrafficLightsFromIntersection(Intersection intersection) {
        return intersection.getRoads().stream()
                .map(Road::getTrafficLight)
                .toList();
    }
}
