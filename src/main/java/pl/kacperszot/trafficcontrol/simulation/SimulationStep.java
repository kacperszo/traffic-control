package pl.kacperszot.trafficcontrol.simulation;

import pl.kacperszot.trafficcontrol.model.Vehicle;

import java.util.List;

public record SimulationStep(List<Vehicle> leftVehicles) {}
