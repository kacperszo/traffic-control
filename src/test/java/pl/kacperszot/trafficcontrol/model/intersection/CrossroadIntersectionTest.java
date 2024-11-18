package pl.kacperszot.trafficcontrol.model.intersection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kacperszot.trafficcontrol.model.Vehicle;
import pl.kacperszot.trafficcontrol.model.VehicleStatus;
import pl.kacperszot.trafficcontrol.model.road.Road;
import pl.kacperszot.trafficcontrol.model.road.RoadDirection;
import pl.kacperszot.trafficcontrol.model.road.SingleLaneTwoWayRoad;
import pl.kacperszot.trafficcontrol.model.Route;
import pl.kacperszot.trafficcontrol.model.trafficlight.TrafficLightSignal;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CrossroadIntersectionTest {
    private CrossroadIntersection intersection;
    private Vehicle mockVehicle;
    private Route mockRoute;

    @BeforeEach
    void setUp() {
        intersection = new CrossroadIntersection();

        mockRoute = mock(Route.class);
        mockVehicle = mock(Vehicle.class);
        when(mockVehicle.getRoute()).thenReturn(mockRoute);
    }

    @Test
    void testGetRoads() {
        List<Road> roads = intersection.getRoads();

        assertEquals(4, roads.size(), "Intersection should have 4 roads");
        assertTrue(roads.stream().anyMatch(road -> road.getDirection() == RoadDirection.NORTH),
                "North road should exist");
        assertTrue(roads.stream().anyMatch(road -> road.getDirection() == RoadDirection.SOUTH),
                "South road should exist");
        assertTrue(roads.stream().anyMatch(road -> road.getDirection() == RoadDirection.EAST),
                "East road should exist");
        assertTrue(roads.stream().anyMatch(road -> road.getDirection() == RoadDirection.WEST),
                "West road should exist");
    }

    @Test
    void testGetRoadByDirection() {
        when(mockRoute.getStart()).thenReturn(RoadDirection.NORTH);

        SingleLaneTwoWayRoad northRoad = intersection.getRoadByDirection(RoadDirection.NORTH);
        assertNotNull(northRoad, "North road should not be null");
        assertEquals(RoadDirection.NORTH, northRoad.getDirection(),
                "Retrieved road should be the north road");
    }

    @Test
    void testAddVehicle() {
        when(mockRoute.getStart()).thenReturn(RoadDirection.EAST);
        intersection.addVehicle(mockVehicle);
        SingleLaneTwoWayRoad eastRoad = intersection.getRoadByDirection(RoadDirection.EAST);
        verify(mockVehicle).getRoute();
    }

    @Test
    void testRemoveVehicle() {
        when(mockRoute.getStart()).thenReturn(RoadDirection.SOUTH);

        intersection.addVehicle(mockVehicle);
        intersection.removeVehicle(mockVehicle);

        verify(mockVehicle).setStatus(VehicleStatus.COMPLETED_CROSSING);

        SingleLaneTwoWayRoad southRoad = intersection.getRoadByDirection(RoadDirection.SOUTH);
    }

    @Test
    void testInitialTrafficLightStates() {
        SingleLaneTwoWayRoad northRoad = intersection.getRoadByDirection(RoadDirection.NORTH);
        SingleLaneTwoWayRoad southRoad = intersection.getRoadByDirection(RoadDirection.SOUTH);
        SingleLaneTwoWayRoad eastRoad = intersection.getRoadByDirection(RoadDirection.EAST);
        SingleLaneTwoWayRoad westRoad = intersection.getRoadByDirection(RoadDirection.WEST);

        assertEquals(northRoad.getTrafficLight().getState(), TrafficLightSignal.GREEN, "North road light should be green");
        assertEquals(southRoad.getTrafficLight().getState(), TrafficLightSignal.GREEN, "South road light should be green");
        assertNotEquals(eastRoad.getTrafficLight().getState(), TrafficLightSignal.GREEN, "East road light should be red");
        assertNotEquals(westRoad.getTrafficLight().getState(), TrafficLightSignal.GREEN, "West road light should be red");
    }
}