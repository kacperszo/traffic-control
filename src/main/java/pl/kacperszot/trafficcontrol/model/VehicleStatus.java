package pl.kacperszot.trafficcontrol.model;

public enum VehicleStatus {
    // Waiting states
    WAITING_TO_ENTER("Vehicle is queued and waiting to enter the intersection"),
    WAITING_AT_RED_LIGHT("Vehicle is at the front of the queue at a red light"),
    WAITING_FOR_CLEARANCE("Vehicle is waiting for intersection to clear despite green light"),

    // Movement states
    APPROACHING("Vehicle is approaching the intersection"),
    ENTERING_INTERSECTION("Vehicle is in the process of entering the intersection"),
    IN_INTERSECTION("Vehicle is currently within the intersection boundaries"),
    EXITING_INTERSECTION("Vehicle is in the process of exiting the intersection"),
    COMPLETED_CROSSING("Vehicle has successfully crossed the intersection"),

    // Turn states
    TURNING_LEFT("Vehicle is executing a left turn"),
    TURNING_RIGHT("Vehicle is executing a right turn"),
    GOING_STRAIGHT("Vehicle is going straight through the intersection"),

    // Special states
    EMERGENCY_VEHICLE("Vehicle is an emergency vehicle with priority"),
    YIELDING("Vehicle is yielding to other traffic or pedestrians"),
    BLOCKED("Vehicle cannot move due to traffic congestion"),
    MALFUNCTIONING("Vehicle is experiencing technical issues");

    private final String description;

    VehicleStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isMoving() {
        return this == APPROACHING ||
                this == ENTERING_INTERSECTION ||
                this == IN_INTERSECTION ||
                this == EXITING_INTERSECTION ||
                this == TURNING_LEFT ||
                this == TURNING_RIGHT ||
                this == GOING_STRAIGHT;
    }

    public boolean isWaiting() {
        return this == WAITING_TO_ENTER ||
                this == WAITING_AT_RED_LIGHT ||
                this == WAITING_FOR_CLEARANCE ||
                this == YIELDING ||
                this == BLOCKED;
    }

    public boolean requiresImmediate_attention() {
        return this == EMERGENCY_VEHICLE ||
                this == MALFUNCTIONING;
    }

    public boolean hasCompletedCrossing() {
        return this == COMPLETED_CROSSING;
    }
}