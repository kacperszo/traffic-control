package pl.kacperszot.trafficcontrol.model.intersection;

import pl.kacperszot.trafficcontrol.model.road.LaneMode;
import pl.kacperszot.trafficcontrol.model.road.Road;
import pl.kacperszot.trafficcontrol.model.road.RoadDirection;
import pl.kacperszot.trafficcontrol.model.road.RoadLane;

import java.util.List;

public class CrossroadIntersection implements Intersection {
    private final Road northRoad;
    private final Road southRoad;
    private final Road westRoad;
    private final Road eastRoad;

    public CrossroadIntersection() {
        this.northRoad = initRoad(RoadDirection.NORTH);
        this.southRoad = initRoad(RoadDirection.SOUTH);
        this.westRoad = initRoad(RoadDirection.WEST);
        this.eastRoad = initRoad(RoadDirection.EAST);
    }

    private Road initRoad(RoadDirection direction) {
        Road road = new Road(direction);

        //add lines
        road.addRoadLane(new RoadLane(LaneMode.ENTRY));
        road.addRoadLane(new RoadLane(LaneMode.EXIT));

        return road;
    }

    @Override
    public List<Road> getRoads() {
        return List.of(northRoad, southRoad, eastRoad, westRoad);
    }
}
