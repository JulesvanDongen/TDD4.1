import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class BoardSpec {
    @Test
    @Tag("2c")
    public void whenNewBoardShouldBeEmpty() {
        Board board = new Board();
        assertTrue(board.getInternalState().isEmpty());
    }

    @Test
    public void whenPlaceTileOnNewBoardThenBoardShouldContainTile() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();
        Board board = new Board(map);
        try {
            Position position = new Position(0, 0);
            Stack<Tile> expected = new Stack<>();
            Tile expectedTile = new Tile(Hive.Player.WHITE, Hive.Tile.BEETLE);
            expected.push(expectedTile);
            board.putTile(position, expectedTile);
            Stack<Tile> result = map.get(position);
            assertEquals(expected, result);
        } catch (IllegalPositionException e) {
            fail("IllegalPositionException was thrown: " + e.getMessage());
        }
    }

    @Test
    @Tag("2f")
    public void whenPlaceTileOnOtherTileThenNotThrowIllegalPositionException() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();
        Position k = new Position(0, 0);
        Tile tile1 = new Tile(Hive.Player.WHITE, Hive.Tile.BEETLE);
        Stack<Tile> tileStack = new Stack<>();
        tileStack.push(tile1);

        map.put(k, tileStack);
        Board board = new Board(map);

        Tile tile = new Tile(Hive.Player.WHITE, Hive.Tile.GRASSHOPPER);
        Stack<Tile> tiles = new Stack<>();
        tiles.push(tile);
        try {
            board.putTile(k, tile);
        } catch (IllegalPositionException e) {
            fail("Exception was thrown");
        }
    }

    @Test
    @Tag("Utility")
    public void whenGetSurroundingTilesWithGrasshopperNearbyThenReturnGrasshopper() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();
        Tile tile = new Tile(Hive.Player.WHITE, Hive.Tile.GRASSHOPPER);
        Stack<Tile> tiles = new Stack<>();
        tiles.push(tile);
        map.put(new Position(1, 0), tiles);
        Board board = new Board(map);

        Position k = new Position(0, 0);

        HashMap<Position, Stack<Tile>> expected = new HashMap<>();
        Stack<Tile> expectedTiles = new Stack<>();
        expectedTiles.push(new Tile(Hive.Player.WHITE, Hive.Tile.GRASSHOPPER));
        expected.put(new Position(1, 0), expectedTiles);

        assertEquals(expected, board.getSurroundingTiles(k));
    }

    @Test
    @Tag("2e")
    public void whenMoveIsUsedOnEmptyTileThenThrowEmptyPositionException() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();

        Board board = new Board(map);
        assertThrows(EmptyPositionException.class, () -> {
            board.moveTile(new Position(0,0), new Position(0,1));
        });
    }

    @Test
    @Tag("2a")
    public void whenMoveIsUsedThenTilePositionChanged() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();
        Tile tile = new Tile(Hive.Player.WHITE, Hive.Tile.GRASSHOPPER);
        Position oldPosition = new Position(0, 0);
        Position newPosition = new Position(0, 1);
        Stack<Tile> tiles = new Stack<>();
        tiles.push(tile);
        map.put(oldPosition, tiles);
        Board board = new Board(map);
        try {
            board.moveTile(oldPosition, newPosition);
            Stack<Tile> movedTile = map.get(newPosition);
            assertEquals(tile, movedTile.peek());
        } catch (EmptyPositionException e) {
            fail("Exception was thrown: " + e.getMessage());
        }
    }

    @Test
    @Tag("2f")
    public void whenStonesAreOnTopOfEachotherAndPositionIsMovedThenTopOneIsMoved() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();
        Stack<Tile> startingTiles = new Stack<>();
        startingTiles.push(new Tile(Hive.Player.WHITE, Hive.Tile.GRASSHOPPER));
        Tile topTile = new Tile(Hive.Player.BLACK, Hive.Tile.BEETLE);
        startingTiles.push(topTile);
        map.put(new Position(0, 0), startingTiles);
        Board board = new Board(map);

        try {
            board.moveTile(new Position(0, 0), new Position(0, 1));
            assertEquals(topTile, map.get(new Position(0,1)).peek());
        } catch (EmptyPositionException e) {
            fail("Exception was thrown: " + e.getMessage());
        }
    }

    @Test
    @Tag("2f")
    public void whenStonesAreOnTopOfEachotherAndPositionIsMovedThenTilesBelowAreNotMoved() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();
        Stack<Tile> startingTiles = new Stack<>();
        Tile bottomTile = new Tile(Hive.Player.WHITE, Hive.Tile.GRASSHOPPER);
        startingTiles.push(bottomTile);
        Tile topTile = new Tile(Hive.Player.BLACK, Hive.Tile.BEETLE);
        startingTiles.push(topTile);
        map.put(new Position(0, 0), startingTiles);
        Board board = new Board(map);

        try {
            board.moveTile(new Position(0, 0), new Position(0, 1));
            assertEquals(bottomTile, map.get(new Position(0,0)).peek());
        } catch (EmptyPositionException e) {
            fail("Exception was thrown: " + e.getMessage());
        }
    }
}
