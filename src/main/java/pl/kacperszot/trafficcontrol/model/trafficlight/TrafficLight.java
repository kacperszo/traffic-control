package pl.kacperszot.trafficcontrol.model.trafficlight;

public class TrafficLight {
    private TrafficLightSignal state;

    public TrafficLight() {
        state = TrafficLightSignal.RED;
    }

    public TrafficLight(TrafficLightSignal state) {
        this.state = state;
    }

    public TrafficLightSignal getState() {
        return state;
    }

    /**
     * Change Traffic Light state to the next one, return new state
     * @return TrafficLightSignal new traffic light state
     */
    public TrafficLightSignal toggleNextState() {
        this.state = switch (state) {
            case RED -> TrafficLightSignal.RED_YELLOW;
            case RED_YELLOW -> TrafficLightSignal.GREEN;
            case GREEN -> TrafficLightSignal.YELLOW;
            case YELLOW -> TrafficLightSignal.RED;
        };
        return this.state;
    }

}
