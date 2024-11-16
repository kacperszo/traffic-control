package pl.kacperszot.trafficcontrol.model.intersection;

import pl.kacperszot.trafficcontrol.model.road.Road;

import java.util.List;

public interface Intersection {
    List<Road> getRoads();
}
