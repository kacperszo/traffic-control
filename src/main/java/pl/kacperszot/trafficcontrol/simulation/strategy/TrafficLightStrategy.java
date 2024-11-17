package pl.kacperszot.trafficcontrol.simulation.strategy;

import pl.kacperszot.trafficcontrol.model.intersection.Intersection;

public interface TrafficLightStrategy {

    void step(Intersection intersection);
}
