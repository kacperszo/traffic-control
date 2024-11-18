package pl.kacperszot.trafficcontrol.simulation.strategy;

import pl.kacperszot.trafficcontrol.model.intersection.Intersection;
import pl.kacperszot.trafficcontrol.model.road.Road;
import pl.kacperszot.trafficcontrol.model.road.RoadDirection;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLight;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLightSignal;

public class EveryTickStateTurnTrafficLightStrategy implements TrafficLightStrategy {
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
        intersection.getRoads().stream().map(Road::getTrafficLight).forEach(TrafficLight::toggleNextState);
    }
}
