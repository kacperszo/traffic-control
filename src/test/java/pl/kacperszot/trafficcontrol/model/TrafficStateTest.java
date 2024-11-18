package pl.kacperszot.trafficcontrol.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kacperszot.trafficcontrol.model.road.Road;
import pl.kacperszot.trafficcontrol.model.road.RoadDirection;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLight;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLightSignal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrafficStateTest {

    private TrafficState trafficState;
    private Vehicle mockVehicle;
    private Road mockRoad;

    @BeforeEach
    void setUp() {
        trafficState = new TrafficState();

        // Create mock objects
        mockVehicle = mock(Vehicle.class);
        mockRoad = mock(Road.class);

        // Set up common mock behaviors
        when(mockVehicle.getRoute()).thenReturn(mock(Route.class));
        when(mockVehicle.getRoute().getStart()).thenReturn(RoadDirection.NORTH);
    }

    @Test
    void testConstructor() {
        // Verify initial state of maps
        assertTrue(trafficState.getWaitingVehicles().isEmpty(),
                "Waiting vehicles map should be initially empty");
        assertTrue(trafficState.getLightStates().isEmpty(),
                "Light states map should be initially empty");
    }

    @Test
    void testAddWaitingVehicle() {
        // Add a vehicle
        trafficState.addWaitingVehicle(mockVehicle);

        // Verify the vehicle was added correctly
        Map<RoadDirection, List<Vehicle>> waitingVehicles = trafficState.getWaitingVehicles();
        assertEquals(1, waitingVehicles.get(RoadDirection.NORTH).size(),
                "Vehicle should be added to the correct road direction");
        assertTrue(waitingVehicles.get(RoadDirection.NORTH).contains(mockVehicle),
                "The specific vehicle should be in the waiting vehicles list");
    }

    @Test
    void testAddMultipleWaitingVehiclesToSameDirection() {
        // Create two mock vehicles for the same direction
        Vehicle anotherMockVehicle = mock(Vehicle.class);
        when(anotherMockVehicle.getRoute()).thenReturn(mock(Route.class));
        when(anotherMockVehicle.getRoute().getStart()).thenReturn(RoadDirection.NORTH);

        // Add two vehicles
        trafficState.addWaitingVehicle(mockVehicle);
        trafficState.addWaitingVehicle(anotherMockVehicle);

        // Verify both vehicles were added
        Map<RoadDirection, List<Vehicle>> waitingVehicles = trafficState.getWaitingVehicles();
        assertEquals(2, waitingVehicles.get(RoadDirection.NORTH).size(),
                "Both vehicles should be added to the same road direction");
    }

    @Test
    void testAddLightStateForRoad() {
        // Create a mock TrafficLight
        TrafficLight mockTrafficLight = mock(TrafficLight.class);
        when(mockTrafficLight.getState()).thenReturn(TrafficLightSignal.GREEN);

        // Set up the mock road
        when(mockRoad.getDirection()).thenReturn(RoadDirection.EAST);
        when(mockRoad.getTrafficLight()).thenReturn(mockTrafficLight);

        // Add light state for the road
        trafficState.addLightStateForRoad(mockRoad);

        // Verify the light state was added correctly
        Map<RoadDirection, TrafficLightSignal> lightStates = trafficState.getLightStates();
        assertEquals(TrafficLightSignal.GREEN, lightStates.get(RoadDirection.EAST),
                "Traffic light state should be correctly added for the road");
    }

    @Test
    void testGetWaitingVehiclesReturnsUnmodifiableMap() {
        // Add a vehicle
        trafficState.addWaitingVehicle(mockVehicle);

        // Attempt to modify the returned map
        Map<RoadDirection, List<Vehicle>> returnedMap = trafficState.getWaitingVehicles();

        // Verify that modifying the returned map throws an exception
        assertThrows(UnsupportedOperationException.class, () -> {
            returnedMap.put(RoadDirection.SOUTH, new ArrayList<>());
        }, "Returned map should be unmodifiable");
    }

    @Test
    void testGetLightStatesReturnsUnmodifiableMap() {
        // Add a light state
        TrafficLight mockTrafficLight = mock(TrafficLight.class);
        when(mockTrafficLight.getState()).thenReturn(TrafficLightSignal.GREEN);
        when(mockRoad.getDirection()).thenReturn(RoadDirection.EAST);
        when(mockRoad.getTrafficLight()).thenReturn(mockTrafficLight);
        trafficState.addLightStateForRoad(mockRoad);

        // Attempt to modify the returned map
        Map<RoadDirection, TrafficLightSignal> returnedMap = trafficState.getLightStates();

        // Verify that modifying the returned map throws an exception
        assertThrows(UnsupportedOperationException.class, () -> {
            returnedMap.put(RoadDirection.SOUTH, TrafficLightSignal.RED);
        }, "Returned map should be unmodifiable");
    }
}