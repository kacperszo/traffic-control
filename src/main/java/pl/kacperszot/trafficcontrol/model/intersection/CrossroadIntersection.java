package pl.kacperszot.trafficcontrol.model.intersection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.kacperszot.trafficcontrol.model.Vehicle;
import pl.kacperszot.trafficcontrol.model.VehicleStatus;
import pl.kacperszot.trafficcontrol.model.road.Road;
import pl.kacperszot.trafficcontrol.model.road.RoadDirection;
import pl.kacperszot.trafficcontrol.model.road.SingleLaneTwoWayRoad;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLight;

import java.util.List;
import java.util.stream.Collectors;

public class CrossroadIntersection implements Intersection {
    private final SingleLaneTwoWayRoad northRoad;
    private final SingleLaneTwoWayRoad southRoad;
    private final SingleLaneTwoWayRoad westRoad;
    private final SingleLaneTwoWayRoad eastRoad;
    private static final Logger LOGGER = LogManager.getLogger();


    public CrossroadIntersection() {
        this.northRoad = new SingleLaneTwoWayRoad(RoadDirection.NORTH, TrafficLight.createGreen());
        this.southRoad = new SingleLaneTwoWayRoad(RoadDirection.SOUTH, TrafficLight.createGreen());
        this.westRoad = new SingleLaneTwoWayRoad(RoadDirection.WEST, TrafficLight.createRed());
        this.eastRoad = new SingleLaneTwoWayRoad(RoadDirection.EAST, TrafficLight.createRed());
    }

    @Override
    public List<Road> getRoads() {
        return List.of(northRoad, southRoad, eastRoad, westRoad);
    }

    public SingleLaneTwoWayRoad getRoadByDirection(RoadDirection direction) {
        return switch (direction) {
            case NORTH -> northRoad;
            case SOUTH -> southRoad;
            case WEST -> westRoad;
            case EAST -> eastRoad;
        };
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        getRoadByDirection(vehicle.getRoute().getStart()).getEntryLine().addVehicle(vehicle);
    }

    public void removeWaitingVehiclesFromWaitingLines() {

    }

    @Override
    public void removeVehicle(Vehicle vehicle) {
        getRoadByDirection(vehicle.getRoute().getStart()).getEntryLine().removeNextVehicle();
        vehicle.setStatus(VehicleStatus.COMPLETED_CROSSING);
        LOGGER.info("Vehicle is completed: " + vehicle);
    }


}
