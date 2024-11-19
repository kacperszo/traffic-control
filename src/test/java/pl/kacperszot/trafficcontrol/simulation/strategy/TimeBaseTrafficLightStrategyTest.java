package pl.kacperszot.trafficcontrol.simulation.strategy;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import pl.kacperszot.trafficcontrol.model.intersection.Intersection;
import pl.kacperszot.trafficcontrol.model.road.Road;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLight;


import java.util.List;

public class TimeBaseTrafficLightStrategyTest {

    @Test
    void testStableState() {
        // Setup the strategy with a 4 ticks per stable state, 1 tick per transition
        TimeBaseTrafficLightStrategy strategy = new TimeBaseTrafficLightStrategy(4, 1);

        // Mock traffic lights
        TrafficLight mockTrafficLight1 = mock(TrafficLight.class);
        TrafficLight mockTrafficLight2 = mock(TrafficLight.class);

        // Mock roads
        Road mockRoad1 = mock(Road.class);
        Road mockRoad2 = mock(Road.class);

        // Mock intersection
        Intersection mockIntersection = mock(Intersection.class);
        when(mockRoad1.getTrafficLight()).thenReturn(mockTrafficLight1);
        when(mockRoad2.getTrafficLight()).thenReturn(mockTrafficLight2);
        when(mockIntersection.getRoads()).thenReturn(List.of(mockRoad1, mockRoad2));

        // Call step method for the first 2 ticks (should not transition yet)
        strategy.step(mockIntersection);
        strategy.step(mockIntersection);

        // Verify traffic lights have not changed state yet
        verify(mockTrafficLight1, times(0)).toggleNextState();
        verify(mockTrafficLight2, times(0)).toggleNextState();
    }

    @Test
    void testTransitionStateAfterStable() {
        // Setup the strategy with 2 ticks per stable state, 1 tick per transition
        TimeBaseTrafficLightStrategy strategy = new TimeBaseTrafficLightStrategy(2, 1);

        // Mock traffic lights
        TrafficLight mockTrafficLight1 = mock(TrafficLight.class);
        TrafficLight mockTrafficLight2 = mock(TrafficLight.class);

        // Mock roads
        Road mockRoad1 = mock(Road.class);
        Road mockRoad2 = mock(Road.class);

        // Mock intersection
        Intersection mockIntersection = mock(Intersection.class);
        when(mockRoad1.getTrafficLight()).thenReturn(mockTrafficLight1);
        when(mockRoad2.getTrafficLight()).thenReturn(mockTrafficLight2);
        when(mockIntersection.getRoads()).thenReturn(List.of(mockRoad1, mockRoad2));

        // Call step method for the first 3 ticks (should transition)
        strategy.step(mockIntersection); // tick 1
        strategy.step(mockIntersection); // tick 2
        strategy.step(mockIntersection); // tick 3 -> transition

        // Verify traffic lights change state after 3 ticks
        verify(mockTrafficLight1, times(1)).toggleNextState();
        verify(mockTrafficLight2, times(1)).toggleNextState();
    }

    @Test
    void testMultipleStepsWithTransition() {
        // Setup the strategy with 2 ticks per stable state, 1 tick per transition
        TimeBaseTrafficLightStrategy strategy = new TimeBaseTrafficLightStrategy(2, 1);

        // Mock traffic lights
        TrafficLight mockTrafficLight1 = mock(TrafficLight.class);
        TrafficLight mockTrafficLight2 = mock(TrafficLight.class);

        // Mock roads
        Road mockRoad1 = mock(Road.class);
        Road mockRoad2 = mock(Road.class);

        // Mock intersection
        Intersection mockIntersection = mock(Intersection.class);
        when(mockRoad1.getTrafficLight()).thenReturn(mockTrafficLight1);
        when(mockRoad2.getTrafficLight()).thenReturn(mockTrafficLight2);
        when(mockIntersection.getRoads()).thenReturn(List.of(mockRoad1, mockRoad2));

        // Step through multiple ticks and transitions
        strategy.step(mockIntersection); // tick 1
        strategy.step(mockIntersection); // tick 2
        strategy.step(mockIntersection); // tick 3 = transition red to yellow_red #1

        verify(mockTrafficLight1, times(1)).toggleNextState();
        verify(mockTrafficLight2, times(1)).toggleNextState();

        // After transition, steps again
        strategy.step(mockIntersection); // tick 4 transition yellow_red to green #2
        strategy.step(mockIntersection); // tick 5 green
        strategy.step(mockIntersection); // tick 6 green
        strategy.step(mockIntersection); // tick 7 green to yellow #3
        strategy.step(mockIntersection); // tick 8 yellow to red #4
        strategy.step(mockIntersection); // tick 9 red
        strategy.step(mockIntersection); // tick 10 red
        verify(mockTrafficLight1, times(4)).toggleNextState();
        verify(mockTrafficLight2, times(4)).toggleNextState();

    }
}
