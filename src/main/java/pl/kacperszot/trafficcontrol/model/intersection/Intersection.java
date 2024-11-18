package pl.kacperszot.trafficcontrol.model.intersection;

import pl.kacperszot.trafficcontrol.model.Vehicle;
import pl.kacperszot.trafficcontrol.model.road.Road;

import java.util.List;

/**
 * Interface defining intersection in the simulation,
 * every intersection needs to have roads, and be able to add and remove vehicles
 */
public interface Intersection {
    /**
     * Get all roads in the intersection
     * @return roads in th intersection
     */
    List<Road> getRoads();

    /**
     * add vehicle to the intersection, the entry lane will be automatically assign based on vehicle route
     * @param vehicle vehicle to add
     */
    void addVehicle(Vehicle vehicle);

    /**
     * remove vehicle from the intersection, mainly use for removing vehicle which already completed intersection,
     * so this method should be optimized for that
     * @param vehicle vehicle to remove
     */
    void removeVehicle(Vehicle vehicle);
}
