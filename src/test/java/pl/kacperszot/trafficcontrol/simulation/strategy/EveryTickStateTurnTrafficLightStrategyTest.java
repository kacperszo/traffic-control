package pl.kacperszot.trafficcontrol.simulation.strategy;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import pl.kacperszot.trafficcontrol.model.intersection.Intersection;
import pl.kacperszot.trafficcontrol.model.road.Road;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLight;
import java.util.List;

public class EveryTickStateTurnTrafficLightStrategyTest {

    @Test
    void testStep() {
        // Create mocks for the traffic lights
        TrafficLight mockTrafficLight1 = mock(TrafficLight.class);
        TrafficLight mockTrafficLight2 = mock(TrafficLight.class);

        // Create mocks for roads
        Road mockRoad1 = mock(Road.class);
        Road mockRoad2 = mock(Road.class);

        // Mock the behavior of roads returning traffic lights
        when(mockRoad1.getTrafficLight()).thenReturn(mockTrafficLight1);
        when(mockRoad2.getTrafficLight()).thenReturn(mockTrafficLight2);

        // Create a mock for the intersection
        Intersection mockIntersection = mock(Intersection.class);

        // Mock the behavior of the intersection returning roads
        when(mockIntersection.getRoads()).thenReturn(List.of(mockRoad1, mockRoad2));

        // Create an instance of the strategy
        EveryTickStateTurnTrafficLightStrategy strategy = new EveryTickStateTurnTrafficLightStrategy();

        // Call the step method
        strategy.step(mockIntersection);

        // Verify that toggleNextState() was called on both traffic lights
        verify(mockTrafficLight1, times(1)).toggleNextState();
        verify(mockTrafficLight2, times(1)).toggleNextState();
    }

    @Test
    void testStepWithMultipleRoads() {
        // Create more mocks for additional roads and traffic lights
        TrafficLight mockTrafficLight1 = mock(TrafficLight.class);
        TrafficLight mockTrafficLight2 = mock(TrafficLight.class);
        TrafficLight mockTrafficLight3 = mock(TrafficLight.class);

        Road mockRoad1 = mock(Road.class);
        Road mockRoad2 = mock(Road.class);
        Road mockRoad3 = mock(Road.class);

        // Mock traffic lights for roads
        when(mockRoad1.getTrafficLight()).thenReturn(mockTrafficLight1);
        when(mockRoad2.getTrafficLight()).thenReturn(mockTrafficLight2);
        when(mockRoad3.getTrafficLight()).thenReturn(mockTrafficLight3);

        // Mock intersection to return roads
        Intersection mockIntersection = mock(Intersection.class);
        when(mockIntersection.getRoads()).thenReturn(List.of(mockRoad1, mockRoad2, mockRoad3));

        // Create strategy instance
        EveryTickStateTurnTrafficLightStrategy strategy = new EveryTickStateTurnTrafficLightStrategy();

        // Call the step method
        strategy.step(mockIntersection);

        // Verify that toggleNextState was called on all three traffic lights
        verify(mockTrafficLight1, times(1)).toggleNextState();
        verify(mockTrafficLight2, times(1)).toggleNextState();
        verify(mockTrafficLight3, times(1)).toggleNextState();
    }
}
