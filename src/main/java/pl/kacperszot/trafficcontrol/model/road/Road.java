package pl.kacperszot.trafficcontrol.model.road;

import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLight;

import java.util.List;

/**
 * Interface defining road, every road have a direction, traffic lights, entry and exit lanes.
 */
public interface Road {
    /**
     * Get traffic light assigned to the road
     * @return TrafficLight assigned to the road
     */
    TrafficLight getTrafficLight();

    /**
     * Get list on entry lanes
     * @return entry lanes
     */
    List<RoadLane> getEntryLanes();

    /**
     * get list of exit lanes
     * @return exit lanes
     */
    List<RoadLane> getExitLanes();

    /**
     * returns road direction, direction tell us to what direction exit lane leads to
     * @return direction
     */
    RoadDirection getDirection();
}
