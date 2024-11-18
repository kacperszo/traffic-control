package pl.kacperszot.trafficcontrol.model.trafficlight;

/**
 * Main traffic light for the Road
 */
public class RoadMainTrafficLight implements TrafficLight {
    private TrafficLightSignal state;

    public RoadMainTrafficLight() {
        state = TrafficLightSignal.RED;
    }

    public RoadMainTrafficLight(TrafficLightSignal state) {
        this.state = state;
    }

    public static RoadMainTrafficLight createGreen() {
        return new RoadMainTrafficLight(TrafficLightSignal.GREEN);
    }

    public static RoadMainTrafficLight createRed() {
        return new RoadMainTrafficLight(TrafficLightSignal.RED);
    }

    @Override
    public TrafficLightSignal getState() {
        return state;
    }

    /**
     * Change Traffic Light state to the next one, return new state
     *
     * @return TrafficLightSignal new traffic light state
     */
    @Override
    public TrafficLightSignal toggleNextState() {
        this.state = switch (state) {
            case RED -> TrafficLightSignal.RED_YELLOW;
            case RED_YELLOW -> TrafficLightSignal.GREEN;
            case GREEN -> TrafficLightSignal.YELLOW;
            case YELLOW -> TrafficLightSignal.RED;
        };
        return this.state;
    }

    @Override
    public void setState(TrafficLightSignal trafficLightSignal) {
        this.state = trafficLightSignal;
    }

}
