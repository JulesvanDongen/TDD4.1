import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GrasshopperSpec {
    @Test
    @Tag("11a")
    void whenGrasshopperGetPossibleMovesThenOnlyGetPositionsWhereGrasshopperCanJumpOverOtherTilesInStraightLine() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();

        Stack<Tile> a = new Stack<>();
        a.push(new Grasshopper(Hive.Player.WHITE));
        map.put(new Position(0, 0), a);

        Stack<Tile> b = new Stack<>();
        b.push(new Grasshopper(Hive.Player.WHITE));
        map.put(new Position(1, 0), b);

        Stack<Tile> c = new Stack<>();
        Grasshopper grasshopper = new Grasshopper(Hive.Player.WHITE);
        c.push(grasshopper);
        Position position = new Position(0, 1);
        map.put(position, c);

        Board board = new Board(map);

        assertAll(() -> {
            assertFalse(grasshopper.getPossibleMoves(board, position).contains(new Position(1, -1)));
        }, () -> {
            assertTrue(grasshopper.getPossibleMoves(board, position).contains(new Position(0, -1)));
        }, () -> {
            assertTrue(grasshopper.getPossibleMoves(board, position).contains(new Position(2, -1)));
        });
    }

    @Test
    @Tag("11b")
    @Disabled
    void whenGrasshopperMovesThenCannotMoveToOwnLocation() {
        
    }

    @Test
    @Tag("11c")
    @Disabled
    void whenGrasshopperGetPossibleMovesThenMustJumpOverOneTile() {

    }

    @Test
    @Tag("11d")
    @Disabled
    void whenGrasshopperTriesToMoveToOccupiedTileThenThrowIllegalMoveException() {

    }

    @Test
    @Tag("11e")
    @Disabled
    void whenGrasshopperMovesThenCannotJumpOverEmptyTiles() {

    }
}
