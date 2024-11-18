package pl.kacperszot.trafficcontrol.model.intersection;

import pl.kacperszot.trafficcontrol.model.Vehicle;
import pl.kacperszot.trafficcontrol.model.road.Road;

import java.util.List;

public interface Intersection {
    List<Road> getRoads();
    void addVehicle(Vehicle vehicle);
    void removeVehicle(Vehicle vehicle);
}
