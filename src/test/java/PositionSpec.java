import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PositionSpec {
    @Test
    @Tag("2b")
    public void whenGetSurroundingPositionsShouldReturnListOfCorrectPositions() {
        Position position = new Position(0, 0);
        ArrayList<Position> surroundingPositions = position.getSurroundingPositions();

        assertAll(
                () -> {
                    assertEquals(6, surroundingPositions.size());
                },
                () -> {
                    assertTrue(surroundingPositions.contains(new Position(0, 1)));
                },
                () -> {
                    assertTrue(surroundingPositions.contains(new Position(1, 0)));
                },
                () -> {
                    assertTrue(surroundingPositions.contains(new Position(1, 1)));
                },
                () -> {
                    assertTrue(surroundingPositions.contains(new Position(-1, 0)));
                },
                () -> {
                    assertTrue(surroundingPositions.contains(new Position(-1, -1)));
                },
                () -> {
                    assertTrue(surroundingPositions.contains(new Position(0, -1)));
                }
                );
    }
}