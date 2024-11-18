package pl.kacperszot.trafficcontrol.model.trafficlight;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RoadMainTrafficLightTest {

    @Test
    void testDefaultConstructor() {
        RoadMainTrafficLight trafficLight = new RoadMainTrafficLight();
        // The default state should be RED
        assertEquals(TrafficLightSignal.RED, trafficLight.getState());
    }

    @Test
    void testConstructorWithInitialState() {
        // Test with initial state RED
        RoadMainTrafficLight trafficLight = new RoadMainTrafficLight(TrafficLightSignal.RED);
        assertEquals(TrafficLightSignal.RED, trafficLight.getState());

        // Test with initial state GREEN
        trafficLight = new RoadMainTrafficLight(TrafficLightSignal.GREEN);
        assertEquals(TrafficLightSignal.GREEN, trafficLight.getState());
    }

    @Test
    void testCreateGreen() {
        RoadMainTrafficLight trafficLight = RoadMainTrafficLight.createGreen();
        // Should create a traffic light with GREEN state
        assertEquals(TrafficLightSignal.GREEN, trafficLight.getState());
    }

    @Test
    void testCreateRed() {
        RoadMainTrafficLight trafficLight = RoadMainTrafficLight.createRed();
        // Should create a traffic light with RED state
        assertEquals(TrafficLightSignal.RED, trafficLight.getState());
    }

    @Test
    void testToggleNextState() {
        // Test the state transitions using toggleNextState()
        RoadMainTrafficLight trafficLight = new RoadMainTrafficLight(TrafficLightSignal.RED);

        // RED -> RED_YELLOW
        assertEquals(TrafficLightSignal.RED_YELLOW, trafficLight.toggleNextState());

        // RED_YELLOW -> GREEN
        assertEquals(TrafficLightSignal.GREEN, trafficLight.toggleNextState());

        // GREEN -> YELLOW
        assertEquals(TrafficLightSignal.YELLOW, trafficLight.toggleNextState());

        // YELLOW -> RED
        assertEquals(TrafficLightSignal.RED, trafficLight.toggleNextState());
    }

    @Test
    void testStateCycle() {
        // Test if the states cycle correctly
        RoadMainTrafficLight trafficLight = new RoadMainTrafficLight(TrafficLightSignal.RED);

        // Ensure the state follows the expected cycle
        assertEquals(TrafficLightSignal.RED, trafficLight.getState());
        trafficLight.toggleNextState();
        assertEquals(TrafficLightSignal.RED_YELLOW, trafficLight.getState());
        trafficLight.toggleNextState();
        assertEquals(TrafficLightSignal.GREEN, trafficLight.getState());
        trafficLight.toggleNextState();
        assertEquals(TrafficLightSignal.YELLOW, trafficLight.getState());
        trafficLight.toggleNextState();
        assertEquals(TrafficLightSignal.RED, trafficLight.getState());  // Back to RED
    }

}
