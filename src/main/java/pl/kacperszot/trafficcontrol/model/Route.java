package pl.kacperszot.trafficcontrol.model;

import pl.kacperszot.trafficcontrol.model.road.RoadDirection;

import java.util.Objects;

public class Route {
    private final RoadDirection start;
    private final RoadDirection end;

    public Route(RoadDirection startDirection, RoadDirection endDirection) {
        this.start = startDirection;
        this.end = endDirection;
    }

    public RoadDirection getStart() {
        return start;
    }

    public RoadDirection getEnd() {
        return end;
    }

    public boolean hasRoutePriority(Route other) {
        // Forward movements always have priority
        if (this.isGoingForward()) {
            return true;
        }

        // Left turns have priority over other left turns or anything other than forward
        if (this.isTurningLeft()) {
            return other.isTurningLeft() || !other.isGoingForward();
        }

        // Default case: no priority
        return false;
    }


    public boolean isTurningRight() {
        return  (start == RoadDirection.NORTH && end == RoadDirection.EAST) ||
                (start == RoadDirection.WEST && end == RoadDirection.NORTH) ||
                (start == RoadDirection.SOUTH && end == RoadDirection.WEST) ||
                (start == RoadDirection.EAST && end == RoadDirection.SOUTH);
    }

    public boolean isTurningLeft() {
        return  (start == RoadDirection.NORTH && end == RoadDirection.WEST) ||
                (start == RoadDirection.WEST && end == RoadDirection.SOUTH) ||
                (start == RoadDirection.SOUTH && end == RoadDirection.EAST) ||
                (start == RoadDirection.EAST && end == RoadDirection.NORTH);
    }

    public boolean isGoingForward() {
        return  (start == RoadDirection.NORTH && end == RoadDirection.SOUTH) ||
                (start == RoadDirection.WEST && end == RoadDirection.EAST) ||
                (start == RoadDirection.SOUTH && end == RoadDirection.NORTH) ||
                (start == RoadDirection.EAST && end == RoadDirection.WEST);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return start == route.start && end == route.end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "Route{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}