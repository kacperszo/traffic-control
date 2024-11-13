package pl.kacperszo.trafficcontrol.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum representing the directions of roads at an intersection.
 */
public enum Road {
    NORTH("north"),
    EAST("east"),
    SOUTH("south"),
    WEST("west");

    private final String direction;

    Road(String direction) {
        this.direction = direction;
    }
    @JsonValue
    public String getJsonValue() {
        return direction;
    }

    @JsonCreator
    public static Road fromJson(String value) {
        for (Road roadDirection : Road.values()) {
            if (roadDirection.direction.equals(value)) {
                return roadDirection;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

}