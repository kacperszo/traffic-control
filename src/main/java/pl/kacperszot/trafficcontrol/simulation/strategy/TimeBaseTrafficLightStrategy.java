package pl.kacperszot.trafficcontrol.simulation.strategy;

import pl.kacperszot.trafficcontrol.model.intersection.Intersection;
import pl.kacperszot.trafficcontrol.model.road.Road;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLight;

import java.util.List;

public class TimeBaseTrafficLightStrategy implements TrafficLightStrategy {
    private final int ticksPerStableState;
    private final int ticksPerTransitionState;

    private int ticksInCurrentState = 0;
    private boolean inTransitionState = false;

    public TimeBaseTrafficLightStrategy(int ticksPerStableState, int ticksPerTransitionState) {
        this.ticksPerStableState = ticksPerStableState;
        this.ticksPerTransitionState = ticksPerTransitionState;
    }

    @Override
    public void step(Intersection intersection) {
        List<TrafficLight> trafficLights = getAllTrafficLightsFromIntersection(intersection);

        if (inTransitionState) {
            trafficLights.forEach(TrafficLight::toggleNextState);
            inTransitionState = false;
            ticksInCurrentState = 0;
        } else {
            if (ticksInCurrentState >= ticksPerStableState) {
                trafficLights.forEach(TrafficLight::toggleNextState);
                inTransitionState = true;
                ticksInCurrentState = 0;
            } else {
                ticksInCurrentState++;
            }
        }

        if (ticksInCurrentState >= ticksPerTransitionState) {
            inTransitionState = true;
        }
    }

    private List<TrafficLight> getAllTrafficLightsFromIntersection(Intersection intersection) {
        return intersection.getRoads().stream()
                .map(Road::getTrafficLight)
                .toList();
    }
}
