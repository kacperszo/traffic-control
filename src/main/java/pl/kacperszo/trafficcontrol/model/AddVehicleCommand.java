package pl.kacperszo.trafficcontrol.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class AddVehicleCommand implements Command {
    private final Vehicle vehicle;

    public AddVehicleCommand(@JsonProperty("vehicleId") String vehicleId, @JsonProperty("startRoad") Road startRoad, @JsonProperty("endRoad") Road endRoad) {
        vehicle = new Vehicle(vehicleId, startRoad, endRoad);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AddVehicleCommand that = (AddVehicleCommand) o;
        return Objects.equals(vehicle, that.vehicle);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(vehicle);
    }

    @Override
    public String toString() {
        return "AddVehicleCommand{" + "vehicle=" + vehicle + '}';
    }
}
