package pl.kacperszo.trafficcontrol.model;

/**
 * Class representing single vehicle in the simulation
 * @param id vehicle's id
 * @param startRoad vehicle's starting road
 * @param endRoad vehicle's ending road
 */
public record Vehicle(String id, Road startRoad, Road endRoad) {}
