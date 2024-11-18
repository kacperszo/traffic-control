package pl.kacperszot.trafficcontrol.simulation.strategy;

import pl.kacperszot.trafficcontrol.model.intersection.Intersection;
import pl.kacperszot.trafficcontrol.model.road.Road;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLightSignal;

public class BrokenTrafficLightStrategy implements TrafficLightStrategy {
    @Override
    public void setup(Intersection intersection) {
        //Turn all light's green and let drivers handle it ;)
        for (Road road : intersection.getRoads()) {
            road.getTrafficLight().setState(TrafficLightSignal.GREEN);
        }
    }

    @Override
    public void step(Intersection intersection) {
        // always green :D
    }
}
