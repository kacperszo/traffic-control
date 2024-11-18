package pl.kacperszot.trafficcontrol.model.trafficlight;

/**
 * Traffic light interface, every traffic light have state and is able to toggle next state in cycle
 */
public interface TrafficLight {
    /**
     * get state
     * @return current traffic light state
     */
    TrafficLightSignal getState();

    /**
     * Toggle next state and return new state
     * @return new traffic light state
     */
    TrafficLightSignal toggleNextState();
}
