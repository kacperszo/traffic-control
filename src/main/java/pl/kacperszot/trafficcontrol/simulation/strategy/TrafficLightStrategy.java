package pl.kacperszot.trafficcontrol.simulation.strategy;

import pl.kacperszot.trafficcontrol.model.intersection.Intersection;

/**
 * Interface defines traffic light change strategy, strategy make decision about changing lights state based on intersection's state
 */
public interface TrafficLightStrategy {
    /**
     Prepare intersections light. ex turn green for one direction red for opposite one
     */
    void setup(Intersection intersection);
    /**
     * Make decision about traffic light change
     * @param intersection current intersection
     */
    void step(Intersection intersection);
}
