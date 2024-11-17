package pl.kacperszot.trafficcontrol.model;

import pl.kacperszot.trafficcontrol.model.road.RoadDirection;

import java.util.Objects;

/**
 * Class representing single vehicle in the simulation
 */
public class Vehicle {
    private final String id;
    private final Route route;
    private VehicleStatus status;

    public Vehicle(String id, RoadDirection startDirection, RoadDirection endDirection) {
        this.id = id;
        this.route = new Route(startDirection, endDirection);
        this.status = VehicleStatus.WAITING_TO_ENTER;
    }

    public String getId() {
        return id;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public Route getRoute() {
        return route;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id) && Objects.equals(route, vehicle.route) && status == vehicle.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, route, status);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id='" + id + '\'' +
                ", route=" + route +
                ", status=" + status +
                '}';
    }
}

