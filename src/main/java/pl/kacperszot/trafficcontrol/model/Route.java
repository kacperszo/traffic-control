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

    /**
     * Drivers are following right hand rule and are always do the best to not collide even if it results in deadlock
     * @param other vehicle I want to check if i can go in the same tick
     * @return if I can go in this tick considering there is vehicle other on intersection
     */
    public boolean hasRoutePriority(Route other) {
        // if routes do not cross, both are privileged
        if (!this.isCollidingWith(other)) {
            return true;
        }

        // turning edge case
        if (this.isTurningRight() && !other.isGoingForward()) {
            return true;
        }

        //right hand rule
        return isRightOf(other.start);
    }

    private boolean isCollidingWith(Route other) {
        if (other.end == end) return true;
        if (isGoingForward() && other.isGoingForward() && isPerpendicularly(this.start, other.start)) return true;
        if (isGoingForward() && other.isGoingForward() && !isPerpendicularly(this.start, other.start)) return false;
        if (isTurningLeft() && other.isGoingForward()) return true;
        if (isGoingForward() && other.isGoingForward()) return true;
        if (other.isTurningLeft() && isGoingForward()) return true;

        return false;
    }

    private boolean isPerpendicularly(RoadDirection a, RoadDirection b) {
        return (a == RoadDirection.EAST && b == RoadDirection.NORTH) ||
                (a == RoadDirection.EAST && b == RoadDirection.SOUTH) ||
                (a == RoadDirection.WEST && b == RoadDirection.NORTH) ||
                (a == RoadDirection.WEST && b == RoadDirection.SOUTH) ||
                (a == RoadDirection.NORTH && b == RoadDirection.EAST) ||
                (a == RoadDirection.NORTH && b == RoadDirection.WEST) ||
                (a == RoadDirection.SOUTH && b == RoadDirection.EAST) ||
                (a == RoadDirection.SOUTH && b == RoadDirection.WEST);
    }


    private boolean isRightOf(RoadDirection otherDirection) {
        return switch (otherDirection) {
            case NORTH -> this.start == RoadDirection.WEST;
            case EAST -> this.start == RoadDirection.NORTH;
            case SOUTH -> this.start == RoadDirection.EAST;
            case WEST -> this.start == RoadDirection.SOUTH;
        };
    }


    private boolean isRightOfDirection(RoadDirection thisDirection, RoadDirection otherDirection) {
        return switch (otherDirection) {
            case NORTH -> thisDirection == RoadDirection.WEST;
            case EAST -> thisDirection == RoadDirection.NORTH;
            case SOUTH -> thisDirection == RoadDirection.EAST;
            case WEST -> thisDirection == RoadDirection.SOUTH;
        };
    }


    public boolean isTurningRight() {
        return (start == RoadDirection.NORTH && end == RoadDirection.WEST) ||
                (start == RoadDirection.WEST && end == RoadDirection.SOUTH) ||
                (start == RoadDirection.SOUTH && end == RoadDirection.EAST) ||
                (start == RoadDirection.EAST && end == RoadDirection.NORTH);
    }

    public boolean isTurningLeft() {
        return (start == RoadDirection.NORTH && end == RoadDirection.EAST) ||
                (start == RoadDirection.WEST && end == RoadDirection.NORTH) ||
                (start == RoadDirection.SOUTH && end == RoadDirection.WEST) ||
                (start == RoadDirection.EAST && end == RoadDirection.SOUTH);
    }

    public boolean isGoingForward() {
        return (start == RoadDirection.NORTH && end == RoadDirection.SOUTH) ||
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