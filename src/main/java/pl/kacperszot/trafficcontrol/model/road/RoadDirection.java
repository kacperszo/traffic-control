package pl.kacperszot.trafficcontrol.model.road;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum representing the directions of roads at an intersection.
 */
public enum RoadDirection {
    NORTH("north"),
    EAST("east"),
    SOUTH("south"),
    WEST("west");

    private final String direction;

    RoadDirection(String direction) {
        this.direction = direction;
    }
    @JsonValue
    public String getJsonValue() {
        return direction;
    }

    @JsonCreator
    public static RoadDirection fromJson(String value) {
        for (RoadDirection roadDirection : RoadDirection.values()) {
            if (roadDirection.direction.equals(value)) {
                return roadDirection;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

}