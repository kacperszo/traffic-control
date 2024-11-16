package pl.kacperszot.trafficcontrol.model.road;

import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLight;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Road {
    private final RoadDirection direction;
    private final List<RoadLane> roadLanes;
    private final TrafficLight trafficLight;

    public Road(RoadDirection direction, TrafficLight trafficLight) {
        this.direction = direction;
        this.trafficLight = trafficLight;
        this.roadLanes = new ArrayList<>();
    }

    public Road(RoadDirection direction) {
        this.direction = direction;
        this.trafficLight = new TrafficLight();
        this.roadLanes = new ArrayList<>();
    }

    public RoadDirection getDirection() {
        return direction;
    }

    public List<RoadLane> getLines() {
        return roadLanes;
    }

    @Override
    public String toString() {
        return "Road{" +
                "direction=" + direction +
                ", lines=" + roadLanes +
                '}';
    }

    public void addRoadLane(RoadLane lane) {
        roadLanes.add(lane);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Road road = (Road) o;
        return direction == road.direction && Objects.equals(roadLanes, road.roadLanes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, roadLanes);
    }

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }
}
