import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

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

        assertFalse(grasshopper.getPossibleMoves(board, position).contains(new Position(1, -1)));
    }

    @Test
    @Tag("11b")
    void whenGrasshopperMovesThenCannotMoveToOwnLocation() {
        Map<Position, Stack<Tile>> map = new HashMap<>();

        Stack<Tile> a = new Stack<>();
        Grasshopper grasshopper = new Grasshopper(Hive.Player.WHITE);
        a.push(grasshopper);
        Position grasshopperPosition = new Position(0, 0);
        map.put(grasshopperPosition, a);

        Board board = new Board(map);

        assertFalse(grasshopper.getPossibleMoves(board, grasshopperPosition).contains(grasshopperPosition));
    }

    @Test
    @Tag("11c")
    void whenGrasshopperGetPossibleMovesThenMustJumpOverOneTile() {
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

        HashSet<Position> expectedPositions = new HashSet<>();
        expectedPositions.add(new Position(0, -1));
        expectedPositions.add(new Position(2, -1));

        assertEquals(expectedPositions, grasshopper.getPossibleMoves(board, position));
    }

    @Test
    @Tag("11d")
    void whenGrasshopperTriesToMoveToOccupiedTileThenThrowIllegalMoveException() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();

        Stack<Tile> a = new Stack<>();
        a.push(new Grasshopper(Hive.Player.WHITE));
        map.put(new Position(0, 0), a);

        Stack<Tile> c = new Stack<>();
        Grasshopper grasshopper = new Grasshopper(Hive.Player.WHITE);
        c.push(grasshopper);
        Position position = new Position(0, 1);
        map.put(position, c);

        Board board = new Board(map);

        assertFalse(grasshopper.getPossibleMoves(board, position).contains(new Position(0,0)));
    }

    @Test
    @Tag("11e")
    @Disabled
    void whenGrasshopperMovesThenCannotJumpOverEmptyTiles() {

    }
}
