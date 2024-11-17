package pl.kacperszot.trafficcontrol.simulation.strategy;

import pl.kacperszot.trafficcontrol.model.intersection.Intersection;
import pl.kacperszot.trafficcontrol.model.road.Road;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLight;

public class EveryTickStateTurnTrafficLightStrategy implements TrafficLightStrategy {
    @Override
    public void step(Intersection intersection) {
        intersection.getRoads().stream().map(Road::getTrafficLight).forEach(TrafficLight::toggleNextState);
    }
}
