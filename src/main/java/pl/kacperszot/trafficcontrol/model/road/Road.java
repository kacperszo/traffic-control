package pl.kacperszot.trafficcontrol.model.road;

import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLight;

import java.util.List;

public interface Road {
    TrafficLight getTrafficLight();
    List<RoadLane> getEntryLanes();
    List<RoadLane> getExitLanes();
    RoadDirection getDirection();
}
