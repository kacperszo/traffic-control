package pl.kacperszot.trafficcontrol.model.road;

import pl.kacperszot.trafficcontrol.model.trafficlight.RoadMainTrafficLight;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLight;

import java.util.List;

public class SingleLaneTwoWayRoad implements Road {
    private final RoadDirection direction;
    private final TrafficLight trafficLight;
    private final RoadLane entryLine;
    private final RoadLane exitLine;


    public SingleLaneTwoWayRoad(RoadDirection direction, TrafficLight trafficLight) {
        this.direction = direction;
        this.trafficLight = trafficLight;
        this.entryLine = new RoadLane(LaneMode.ENTRY);
        this.exitLine = new RoadLane(LaneMode.EXIT);

    }

    public SingleLaneTwoWayRoad(RoadDirection direction) {
        this(direction, new RoadMainTrafficLight());
    }

    public RoadLane getEntryLine() {
        return entryLine;
    }

    public RoadLane getExitLine() {
        return exitLine;
    }

    public RoadDirection getDirection() {
        return direction;
    }

    @Override
    public int getWaitingVehicleCount() {
        return getEntryLanes().stream().mapToInt(RoadLane::size).sum();
    }

    @Override
    public String toString() {
        return "Road{" + "direction=" + direction + ", entryLine=" + entryLine + ", exitLine=" + exitLine + '}';
    }


    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    @Override
    public List<RoadLane> getEntryLanes() {
        return List.of(entryLine);
    }

    @Override
    public List<RoadLane> getExitLanes() {
        return List.of(exitLine);
    }
}
