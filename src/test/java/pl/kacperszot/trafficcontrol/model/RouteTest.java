package pl.kacperszot.trafficcontrol.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pl.kacperszot.trafficcontrol.model.road.RoadDirection;

class RouteTest {

    @Test
    void testRouteCreation() {
        Route route = new Route(RoadDirection.NORTH, RoadDirection.SOUTH);
        assertEquals(RoadDirection.NORTH, route.getStart());
        assertEquals(RoadDirection.SOUTH, route.getEnd());
    }

    @Test
    void testIsGoingForward() {
        assertTrue(new Route(RoadDirection.NORTH, RoadDirection.SOUTH).isGoingForward());
        assertTrue(new Route(RoadDirection.WEST, RoadDirection.EAST).isGoingForward());
        assertTrue(new Route(RoadDirection.SOUTH, RoadDirection.NORTH).isGoingForward());
        assertTrue(new Route(RoadDirection.EAST, RoadDirection.WEST).isGoingForward());

        assertFalse(new Route(RoadDirection.NORTH, RoadDirection.EAST).isGoingForward());
    }

    @Test
    void testIsTurningLeft() {
        assertTrue(new Route(RoadDirection.NORTH, RoadDirection.WEST).isTurningLeft());
        assertTrue(new Route(RoadDirection.WEST, RoadDirection.SOUTH).isTurningLeft());
        assertTrue(new Route(RoadDirection.SOUTH, RoadDirection.EAST).isTurningLeft());
        assertTrue(new Route(RoadDirection.EAST, RoadDirection.NORTH).isTurningLeft());

        assertFalse(new Route(RoadDirection.NORTH, RoadDirection.EAST).isTurningLeft());
    }

    @Test
    void testIsTurningRight() {
        assertTrue(new Route(RoadDirection.NORTH, RoadDirection.EAST).isTurningRight());
        assertTrue(new Route(RoadDirection.WEST, RoadDirection.NORTH).isTurningRight());
        assertTrue(new Route(RoadDirection.SOUTH, RoadDirection.WEST).isTurningRight());
        assertTrue(new Route(RoadDirection.EAST, RoadDirection.SOUTH).isTurningRight());

        assertFalse(new Route(RoadDirection.NORTH, RoadDirection.WEST).isTurningRight());
    }

    @Test
    void testHasRoutePriority() {
        Route forwardRoute = new Route(RoadDirection.NORTH, RoadDirection.SOUTH);
        Route leftTurn = new Route(RoadDirection.NORTH, RoadDirection.WEST);
        Route rightTurn = new Route(RoadDirection.NORTH, RoadDirection.EAST);

        // Forward movement always has priority
        assertTrue(forwardRoute.hasRoutePriority(leftTurn));
        assertTrue(forwardRoute.hasRoutePriority(rightTurn));

        // Left turns have priority over non-forward movements
        assertTrue(leftTurn.hasRoutePriority(rightTurn));

        // Left turns have priority over other left turns
        assertTrue(leftTurn.hasRoutePriority(new Route(RoadDirection.WEST, RoadDirection.NORTH)));

        // Non-forward movements without left turn have no priority
        assertFalse(rightTurn.hasRoutePriority(leftTurn));
    }

    @Test
    void testEquals() {
        Route route1 = new Route(RoadDirection.NORTH, RoadDirection.SOUTH);
        Route route2 = new Route(RoadDirection.NORTH, RoadDirection.SOUTH);
        Route differentRoute = new Route(RoadDirection.NORTH, RoadDirection.EAST);

        assertEquals(route1, route2);
        assertNotEquals(route1, differentRoute);
        assertNotEquals(route1, null);
    }
}