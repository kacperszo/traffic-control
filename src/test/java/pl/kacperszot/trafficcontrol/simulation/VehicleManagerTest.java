package pl.kacperszot.trafficcontrol.simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kacperszot.trafficcontrol.model.Route;
import pl.kacperszot.trafficcontrol.model.TrafficState;
import pl.kacperszot.trafficcontrol.model.Vehicle;
import pl.kacperszot.trafficcontrol.model.VehicleStatus;
import pl.kacperszot.trafficcontrol.model.road.RoadDirection;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLightSignal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VehicleManagerTest {

    private VehicleManager vehicleManager;
    private TrafficState mockTrafficState;

    @BeforeEach
    void setUp() {
        vehicleManager = new VehicleManager();
        mockTrafficState = mock(TrafficState.class);
    }

    @Test
    void testStepWithNoVehicles() {
        // Simulate empty traffic state
        when(mockTrafficState.getLightStates()).thenReturn(new HashMap<>());
        when(mockTrafficState.getWaitingVehicles()).thenReturn(new HashMap<>());

        SimulationStep result = vehicleManager.step(mockTrafficState);

        assertTrue(result.leftVehicles().isEmpty(),
                "No vehicles should leave when no vehicles are present");
    }

    @Test
    void testStepWithVehiclesAtRedLight() {
        // Setup mock traffic state with red light
        Map<RoadDirection, TrafficLightSignal> lightStates = new HashMap<>();
        lightStates.put(RoadDirection.NORTH, TrafficLightSignal.RED);

        Vehicle mockVehicle = createMockVehicle(RoadDirection.NORTH);
        Map<RoadDirection, List<Vehicle>> waitingVehicles = Map.of(RoadDirection.NORTH, List.of(mockVehicle));

        when(mockTrafficState.getLightStates()).thenReturn(lightStates);
        when(mockTrafficState.getWaitingVehicles()).thenReturn(waitingVehicles);

        SimulationStep result = vehicleManager.step(mockTrafficState);

        assertTrue(result.leftVehicles().isEmpty(),
                "No vehicles should leave at red light");
        verify(mockVehicle).setStatus(VehicleStatus.WAITING_AT_RED_LIGHT);
    }

    @Test
    void testStepWithVehiclesAtGreenLight() {
        // Setup mock traffic state with green light
        Map<RoadDirection, TrafficLightSignal> lightStates = new HashMap<>();
        lightStates.put(RoadDirection.NORTH, TrafficLightSignal.GREEN);

        Vehicle mockVehicle1 = createMockVehicle(RoadDirection.NORTH);
        Vehicle mockVehicle2 = createMockVehicle(RoadDirection.NORTH);

        // Ensure mockVehicle1 has route priority
        Route mockRoute1 = mock(Route.class);
        Route mockRoute2 = mock(Route.class);
        when(mockVehicle1.getRoute()).thenReturn(mockRoute1);
        when(mockVehicle2.getRoute()).thenReturn(mockRoute2);
        when(mockRoute1.hasRoutePriority(mockRoute2)).thenReturn(true);

        Map<RoadDirection, List<Vehicle>> waitingVehicles = Map.of(RoadDirection.NORTH, List.of(mockVehicle1, mockVehicle2));

        when(mockTrafficState.getLightStates()).thenReturn(lightStates);
        when(mockTrafficState.getWaitingVehicles()).thenReturn(waitingVehicles);

        SimulationStep result = vehicleManager.step(mockTrafficState);

        assertFalse(result.leftVehicles().isEmpty(),
                "Vehicles with route priority should leave");
        verify(mockVehicle1).setStatus(VehicleStatus.IN_INTERSECTION);
    }

    @Test
    void testStepWithBlockedVehicles() {
        // Setup mock traffic state with green light
        Map<RoadDirection, TrafficLightSignal> lightStates = new HashMap<>();
        lightStates.put(RoadDirection.NORTH, TrafficLightSignal.GREEN);

        Vehicle mockVehicle1 = createMockVehicle(RoadDirection.NORTH);
        Vehicle mockVehicle2 = createMockVehicle(RoadDirection.NORTH);

        // Ensure mockVehicle1 does not have route priority over mockVehicle2
        Route mockRoute1 = mock(Route.class);
        Route mockRoute2 = mock(Route.class);
        when(mockVehicle1.getRoute()).thenReturn(mockRoute1);
        when(mockVehicle2.getRoute()).thenReturn(mockRoute2);
        when(mockRoute1.hasRoutePriority(mockRoute2)).thenReturn(false);

        Map<RoadDirection, List<Vehicle>> waitingVehicles = Map.of(RoadDirection.NORTH, List.of(mockVehicle1, mockVehicle2));

        when(mockTrafficState.getLightStates()).thenReturn(lightStates);
        when(mockTrafficState.getWaitingVehicles()).thenReturn(waitingVehicles);

        SimulationStep result = vehicleManager.step(mockTrafficState);

        assertTrue(result.leftVehicles().isEmpty(),
                "No vehicles should leave when blocked");
        verify(mockVehicle1).setStatus(VehicleStatus.BLOCKED);
    }

    // Helper method to create mock vehicle
    private Vehicle createMockVehicle(RoadDirection direction) {
        Vehicle mockVehicle = mock(Vehicle.class);
        Route mockRoute = mock(Route.class);
        when(mockVehicle.getRoute()).thenReturn(mockRoute);
        when(mockRoute.getStart()).thenReturn(direction);
        return mockVehicle;
    }
}