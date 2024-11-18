package pl.kacperszot.trafficcontrol.model.road;

import pl.kacperszot.trafficcontrol.model.Vehicle;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class RoadLane {
    private final LaneMode laneMode;
    private final Queue<Vehicle> vehicles;

    public RoadLane(LaneMode laneMode) {
        this.laneMode = laneMode;
        this.vehicles = new LinkedList<>();
    }

    public Vehicle getNextVehicle() {
        return vehicles.poll();
    }

    public Vehicle peekNextVehicle() {
        return vehicles.peek();
    }

    public LaneMode getLaneMode() {
        return laneMode;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    @Override
    public boolean equals(Object o) {

        if (o == null || getClass() != o.getClass()) return false;
        RoadLane roadLane = (RoadLane) o;
        return laneMode == roadLane.laneMode && Objects.equals(vehicles, roadLane.vehicles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(laneMode, vehicles);
    }

    @Override
    public String toString() {
        return "RoadLane{" +
                "laneMode=" + laneMode +
                ", vehicles=" + vehicles +
                '}';
    }

    public void clear() {
        this.vehicles.clear();
    }

    public void removeNextVehicle() {
        this.vehicles.remove();
    }

    public void removeVehicle(Vehicle vehicle) {
        this.vehicles.remove(vehicle);
    }

    public int size() {
        return vehicles.size();
    }
}
