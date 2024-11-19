package pl.kacperszot.trafficcontrol.simulation.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kacperszot.trafficcontrol.model.intersection.Intersection;
import pl.kacperszot.trafficcontrol.model.road.Road;
import pl.kacperszot.trafficcontrol.model.road.RoadDirection;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLight;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLightSignal;

import java.util.Arrays;

import static org.mockito.Mockito.*;

class AdaptiveProportionalTrafficLightStrategyTest {

    @Mock
    private Intersection intersection;

    @Mock
    private Road northRoad;
    @Mock
    private Road southRoad;
    @Mock
    private Road eastRoad;
    @Mock
    private Road westRoad;

    @Mock
    private TrafficLight northLight;
    @Mock
    private TrafficLight southLight;
    @Mock
    private TrafficLight eastLight;
    @Mock
    private TrafficLight westLight;

    private AdaptiveProportionalTrafficLightStrategy strategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        //road directions
        when(northRoad.getDirection()).thenReturn(RoadDirection.NORTH);
        when(southRoad.getDirection()).thenReturn(RoadDirection.SOUTH);
        when(eastRoad.getDirection()).thenReturn(RoadDirection.EAST);
        when(westRoad.getDirection()).thenReturn(RoadDirection.WEST);

        // traffic lights for roads
        when(northRoad.getTrafficLight()).thenReturn(northLight);
        when(southRoad.getTrafficLight()).thenReturn(southLight);
        when(eastRoad.getTrafficLight()).thenReturn(eastLight);
        when(westRoad.getTrafficLight()).thenReturn(westLight);

        // intersection with roads
        when(intersection.getRoads()).thenReturn(Arrays.asList(northRoad, southRoad, eastRoad, westRoad));

        strategy = new AdaptiveProportionalTrafficLightStrategy(1, 10, 1, 1);
    }

    @Test
    void setup_ShouldInitializeCorrectly() {
        strategy.setup(intersection);

        // Verify NORTH-SOUTH gets GREEN
        verify(northLight).setState(TrafficLightSignal.GREEN);
        verify(southLight).setState(TrafficLightSignal.GREEN);

        // Verify EAST-WEST gets RED
        verify(eastLight).setState(TrafficLightSignal.RED);
        verify(westLight).setState(TrafficLightSignal.RED);
    }

    @Test
    void step_ShouldRespectMinimumGreenTime() {
        // Setup
        when(northLight.getState()).thenReturn(TrafficLightSignal.GREEN);
        when(southLight.getState()).thenReturn(TrafficLightSignal.GREEN);
        when(eastLight.getState()).thenReturn(TrafficLightSignal.RED);
        when(westLight.getState()).thenReturn(TrafficLightSignal.RED);

        // No waiting vehicles
        when(northRoad.getWaitingVehicleCount()).thenReturn(0);
        when(southRoad.getWaitingVehicleCount()).thenReturn(0);
        when(eastRoad.getWaitingVehicleCount()).thenReturn(0);
        when(westRoad.getWaitingVehicleCount()).thenReturn(0);

        strategy.setup(intersection);

        // Step
        strategy.step(intersection);

        // Verify no state changes
        verify(northLight, never()).toggleNextState();
        verify(southLight, never()).toggleNextState();
        verify(eastLight, never()).toggleNextState();
        verify(westLight, never()).toggleNextState();
    }

    @Test
    void step_ShouldRespectMaximumGreenTime() {
        // Setup
        when(northLight.getState()).thenReturn(TrafficLightSignal.GREEN);
        when(southLight.getState()).thenReturn(TrafficLightSignal.GREEN);
        when(eastLight.getState()).thenReturn(TrafficLightSignal.RED);
        when(westLight.getState()).thenReturn(TrafficLightSignal.RED);

        // (should hit max)
        when(northRoad.getWaitingVehicleCount()).thenReturn(20);
        when(southRoad.getWaitingVehicleCount()).thenReturn(20);
        when(eastRoad.getWaitingVehicleCount()).thenReturn(20);
        when(westRoad.getWaitingVehicleCount()).thenReturn(20);

        strategy.setup(intersection);

        // Step through max green time
        for (int i = 0; i < 10; i++) {
            strategy.step(intersection);
        }


        strategy.step(intersection);
        verify(northLight).toggleNextState();
        verify(southLight).toggleNextState();
        verify(eastLight).toggleNextState();
        verify(westLight).toggleNextState();
    }

    @Test
    void step_ShouldHandleTransitionStates() {
        when(northLight.getState()).thenReturn(TrafficLightSignal.GREEN);
        when(southLight.getState()).thenReturn(TrafficLightSignal.GREEN);
        when(eastLight.getState()).thenReturn(TrafficLightSignal.RED);
        when(westLight.getState()).thenReturn(TrafficLightSignal.RED);

        strategy.setup(intersection);

        // Steps
        for (int i = 0; i < 2; i++) {
            strategy.step(intersection);
        }

        //(GREEN -> YELLOW and RED -> RED_YELLOW)
        strategy.step(intersection);
        verify(northLight, times(1)).toggleNextState();
        verify(southLight, times(1)).toggleNextState();
        verify(eastLight, times(1)).toggleNextState();
        verify(westLight, times(1)).toggleNextState();

        //Step
        strategy.step(intersection);

        //(YELLOW -> RED and RED_YELLOW -> GREEN)
        verify(northLight, times(2)).toggleNextState();
        verify(southLight, times(2)).toggleNextState();
        verify(eastLight, times(2)).toggleNextState();
        verify(westLight, times(2)).toggleNextState();
    }

    @Test
    void step_ShouldAdjustGreenTimeBasedOnWaitingVehicles() {
        when(northLight.getState()).thenReturn(TrafficLightSignal.GREEN);
        when(southLight.getState()).thenReturn(TrafficLightSignal.GREEN);
        when(eastLight.getState()).thenReturn(TrafficLightSignal.RED);
        when(westLight.getState()).thenReturn(TrafficLightSignal.RED);

        // 5 waiting vehicles should result in 6 ticks green time (min + vehicles * factor)
        when(northRoad.getWaitingVehicleCount()).thenReturn(5);
        when(southRoad.getWaitingVehicleCount()).thenReturn(0);
        when(eastRoad.getWaitingVehicleCount()).thenReturn(0);
        when(westRoad.getWaitingVehicleCount()).thenReturn(0);

        strategy.setup(intersection);

        // (6 ticks)
        for (int i = 0; i < 6; i++) {
            strategy.step(intersection);
            verify(northLight, never()).toggleNextState();
        }

        // Verify state change on next tick
        strategy.step(intersection);
        verify(northLight).toggleNextState();
    }
}