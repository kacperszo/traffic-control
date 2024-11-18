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
    void testIsTurningRight() {
        // Right turns: (start -> end)
        assertTrue(new Route(RoadDirection.NORTH, RoadDirection.WEST).isTurningRight());
        assertTrue(new Route(RoadDirection.WEST, RoadDirection.SOUTH).isTurningRight());
        assertTrue(new Route(RoadDirection.SOUTH, RoadDirection.EAST).isTurningRight());
        assertTrue(new Route(RoadDirection.EAST, RoadDirection.NORTH).isTurningRight());

        // Non-right turns (i.e., not turning right):
        assertFalse(new Route(RoadDirection.NORTH, RoadDirection.EAST).isTurningRight());
        assertFalse(new Route(RoadDirection.WEST, RoadDirection.NORTH).isTurningRight());
        assertFalse(new Route(RoadDirection.SOUTH, RoadDirection.WEST).isTurningRight());
        assertFalse(new Route(RoadDirection.EAST, RoadDirection.SOUTH).isTurningRight());
    }

    @Test
    void testIsTurningLeft() {
        // Left turns: (start -> end)
        assertTrue(new Route(RoadDirection.NORTH, RoadDirection.EAST).isTurningLeft());
        assertTrue(new Route(RoadDirection.WEST, RoadDirection.NORTH).isTurningLeft());
        assertTrue(new Route(RoadDirection.SOUTH, RoadDirection.WEST).isTurningLeft());
        assertTrue(new Route(RoadDirection.EAST, RoadDirection.SOUTH).isTurningLeft());

        // Non-left turns (i.e., not turning left):
        assertFalse(new Route(RoadDirection.NORTH, RoadDirection.WEST).isTurningLeft());
        assertFalse(new Route(RoadDirection.WEST, RoadDirection.SOUTH).isTurningLeft());
        assertFalse(new Route(RoadDirection.SOUTH, RoadDirection.EAST).isTurningLeft());
        assertFalse(new Route(RoadDirection.EAST, RoadDirection.NORTH).isTurningLeft());
    }

    @Test
    void testHasRoutePriority() {
       Route r1 = new Route(RoadDirection.NORTH, RoadDirection.WEST);
       Route r2 = new Route(RoadDirection.SOUTH, RoadDirection.WEST);
       Route r3 = new Route(RoadDirection.WEST, RoadDirection.EAST);
       Route r4 = new Route(RoadDirection.SOUTH, RoadDirection.NORTH);
       Route r5 = new Route(RoadDirection.EAST, RoadDirection.WEST);

       assertTrue(r1.hasRoutePriority(r2));
       assertFalse(r2.hasRoutePriority(r1));

       assertTrue(r1.hasRoutePriority(r3));
       assertTrue(r3.hasRoutePriority(r1));

       assertTrue(r2.hasRoutePriority(r3));
       assertFalse(r3.hasRoutePriority(r2));

       assertTrue(r4.hasRoutePriority(r3));
       assertFalse(r3.hasRoutePriority(r4));

       assertTrue(r5.hasRoutePriority(r2));
       assertFalse(r2.hasRoutePriority(r5));

    }

    @Test
    void testEquals() {
        Route route1 = new Route(RoadDirection.NORTH, RoadDirection.SOUTH);
        Route route2 = new Route(RoadDirection.NORTH, RoadDirection.SOUTH);
        Route differentRoute = new Route(RoadDirection.NORTH, RoadDirection.EAST);

        assertEquals(route1, route2);
        assertNotEquals(route1, differentRoute);
        assertNotEquals(null, route1);
    }
}