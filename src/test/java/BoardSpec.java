import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class BoardSpec {
    @Test
    public void whenNewBoardShouldBeEmpty() {
        Board board = new Board();
        assertTrue(board.getInternalState().isEmpty());
    }

    @Test
    public void whenPlaceTileOnNewBoardThenBoardShouldContainTile() {
        HashMap<Position, Tile> map = new HashMap<>();
        Board board = new Board(map);
        try {
            Position position = new Position(0, 0);
            Tile expected = new Tile(Hive.Player.WHITE, Hive.Tile.BEETLE);
            board.putTile(position, expected);
            Tile result = map.get(position);
            assertEquals(expected, result);
        } catch (IllegalPositionException e) {
            fail("IllegalPositionException was thrown: " + e.getMessage());
        }
    }

    @Test
    public void whenPlaceTileOnOtherTileThenThrowIllegalPositionException() {
        HashMap<Position, Tile> map = new HashMap<>();
        Position k = new Position(0, 0);
        map.put(k, new Tile(Hive.Player.WHITE, Hive.Tile.BEETLE));
        Board board = new Board(map);

        assertThrows(IllegalPositionException.class, () -> {
            board.putTile(k, new Tile(Hive.Player.WHITE, Hive.Tile.GRASSHOPPER));
        });
    }

    @Test
    public void whenGetSurroundingTilesWithGrasshopperNearbyThenReturnGrasshopper() {
        HashMap<Position, Tile> map = new HashMap<>();
        map.put(new Position(1, 0), new Tile(Hive.Player.WHITE, Hive.Tile.GRASSHOPPER));
        Board board = new Board(map);

        Position k = new Position(0, 0);

        HashMap<Position, Tile> actual = new HashMap<>();
        actual.put(new Position(1, 0), new Tile(Hive.Player.WHITE, Hive.Tile.GRASSHOPPER));

        assertEquals(board.getSurroundingTiles(k), actual);
    }

    @Test
    public void whenMoveIsUsedOnEmptyTileThenThrowEmptyPositionException() {
        HashMap<Position, Tile> map = new HashMap<>();

        Board board = new Board(map);
        assertThrows(EmptyPositionException.class, () -> {
            board.moveTile(new Position(0,0), new Position(0,1));
        });
    }

    @Test
    public void whenMoveIsUsedThenTilePositionChanged() {
        HashMap<Position, Tile> map = new HashMap<>();
        Tile tile = new Tile(Hive.Player.WHITE, Hive.Tile.GRASSHOPPER);
        Position oldPosition = new Position(0, 0);
        Position newPosition = new Position(0, 1);
        map.put(oldPosition, tile);
        Board board = new Board(map);
        try {
            board.moveTile(oldPosition, newPosition);
            Tile movedTile = map.get(newPosition);
            assertEquals(tile, movedTile);
        } catch (EmptyPositionException e) {
            fail("Exception was thrown: " + e.getMessage());
        }
    }
}
