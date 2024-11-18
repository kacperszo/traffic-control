package pl.kacperszot.trafficcontrol.simulation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kacperszot.trafficcontrol.model.Vehicle;
import pl.kacperszot.trafficcontrol.model.VehicleStatus;
import pl.kacperszot.trafficcontrol.model.intersection.Intersection;
import pl.kacperszot.trafficcontrol.model.road.Road;
import pl.kacperszot.trafficcontrol.model.road.RoadLane;
import pl.kacperszot.trafficcontrol.simulation.strategy.TrafficLightStrategy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SimulationManagerTest {

    private SimulationManager simulationManager;
    private Intersection mockIntersection;
    private TrafficLightStrategy mockTrafficLightStrategy;
    private VehicleManager mockVehicleManager;

    @BeforeEach
    void setUp() {
        mockIntersection = mock(Intersection.class);
        mockTrafficLightStrategy = mock(TrafficLightStrategy.class);
        mockVehicleManager = mock(VehicleManager.class);

        simulationManager = new SimulationManager(
                mockIntersection,
                mockTrafficLightStrategy,
                mockVehicleManager
        );
    }

    @Test
    void testAddVehicle() {
        Vehicle mockVehicle = mock(Vehicle.class);

        simulationManager.addVehicle(mockVehicle);

        verify(mockIntersection).addVehicle(mockVehicle);
    }

    @Test
    void testStepWithNoVehicles() {
        // Simulate intersection with no roads or vehicles
        when(mockIntersection.getRoads()).thenReturn(List.of());
        when(mockVehicleManager.step(any())).thenReturn(new SimulationStep(List.of()));

        SimulationStep result = simulationManager.step();

        verify(mockIntersection).getRoads();
        verify(mockVehicleManager).step(any());
        verify(mockTrafficLightStrategy).step(mockIntersection);
        assertTrue(result.leftVehicles().isEmpty());
    }

    @Test
    void testStepWithVehiclesOnLane() {
        // Prepare mocks
        Road mockRoad = mock(Road.class);
        RoadLane mockLane = mock(RoadLane.class);
        Vehicle mockVehicle = mock(Vehicle.class);

        // Setup expectations
        when(mockIntersection.getRoads()).thenReturn(List.of(mockRoad));
        when(mockRoad.getEntryLanes()).thenReturn(List.of(mockLane));
        when(mockLane.peekNextVehicle()).thenReturn(mockVehicle);

        SimulationStep mockSimulationStep = new SimulationStep(List.of(mockVehicle));
        when(mockVehicleManager.step(any())).thenReturn(mockSimulationStep);

        // Execute step
        SimulationStep result = simulationManager.step();

        // Verify interactions
        verify(mockVehicle).setStatus(VehicleStatus.APPROACHING);
        verify(mockIntersection).removeVehicle(mockVehicle);
        verify(mockTrafficLightStrategy).step(mockIntersection);

        assertFalse(result.leftVehicles().isEmpty());
    }

    @Test
    void testStepWithMultipleRoadsAndLanes() {
        // Prepare multiple roads and lanes
        Road mockRoad1 = mock(Road.class);
        Road mockRoad2 = mock(Road.class);
        RoadLane mockLane1 = mock(RoadLane.class);
        RoadLane mockLane2 = mock(RoadLane.class);
        Vehicle mockVehicle1 = mock(Vehicle.class);
        Vehicle mockVehicle2 = mock(Vehicle.class);

        // Setup expectations
        when(mockIntersection.getRoads()).thenReturn(List.of(mockRoad1, mockRoad2));
        when(mockRoad1.getEntryLanes()).thenReturn(List.of(mockLane1));
        when(mockRoad2.getEntryLanes()).thenReturn(List.of(mockLane2));
        when(mockLane1.peekNextVehicle()).thenReturn(mockVehicle1);
        when(mockLane2.peekNextVehicle()).thenReturn(mockVehicle2);

        SimulationStep mockSimulationStep = new SimulationStep(List.of(mockVehicle1, mockVehicle2));
        when(mockVehicleManager.step(any())).thenReturn(mockSimulationStep);

        // Execute step
        SimulationStep result = simulationManager.step();

        // Verify interactions
        verify(mockVehicle1).setStatus(VehicleStatus.APPROACHING);
        verify(mockVehicle2).setStatus(VehicleStatus.APPROACHING);
        verify(mockIntersection, times(2)).removeVehicle(any());
        verify(mockTrafficLightStrategy).step(mockIntersection);

        assertEquals(2, result.leftVehicles().size());
    }
}