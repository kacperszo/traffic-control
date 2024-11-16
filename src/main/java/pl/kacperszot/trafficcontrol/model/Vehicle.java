package pl.kacperszot.trafficcontrol.model;

import pl.kacperszot.trafficcontrol.model.road.RoadDirection;

/**
 * Class representing single vehicle in the simulation
 * @param id vehicle's id
 * @param startRoad vehicle's starting road
 * @param endRoad vehicle's ending road
 */
public record Vehicle(String id, RoadDirection startRoad, RoadDirection endRoad) {}
