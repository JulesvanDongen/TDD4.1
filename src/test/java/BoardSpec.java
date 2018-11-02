import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
            Tile expectedTile = mock(Beetle.class);
            expected.push(expectedTile);
            board.putTile(position, expectedTile);
            Stack<Tile> result = map.get(position);
            assertEquals(expected, result);
        } catch (Hive.IllegalMove e) {
            fail("IllegalPositionException was thrown: " + e.getMessage());
        }
    }

    @Test
    @Tag("2f")
    public void whenPlaceTileOnOtherTileThenNotThrowIllegalPositionException() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();
        Position k = new Position(0, 0);
        Tile tile1 = mock(Beetle.class);
        Stack<Tile> tileStack = new Stack<>();
        tileStack.push(tile1);

        map.put(k, tileStack);
        Board board = new Board(map);

        Tile tile = mock(Beetle.class);
        Stack<Tile> tiles = new Stack<>();
        tiles.push(tile);
        try {
            board.putTile(k, tile);
        } catch (Hive.IllegalMove e) {
            fail("Exception was thrown: " + e.getMessage());
        }
    }

    @Test
    @Tag("Utility")
    public void whenGetSurroundingTilesWithGrasshopperNearbyThenReturnGrasshopper() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();
        Tile tile = mock(Beetle.class);
        Stack<Tile> tiles = new Stack<>();
        tiles.push(tile);
        map.put(new Position(1, 0), tiles);
        Board board = new Board(map);

        Position k = new Position(0, 0);

        HashMap<Position, Stack<Tile>> expected = new HashMap<>();
        Stack<Tile> expectedTiles = new Stack<>();
        expectedTiles.push(tile);
        expected.put(new Position(1, 0), expectedTiles);

        assertEquals(expected, board.getSurroundingTiles(k));
    }

    @Test
    @Tag("2e")
    public void whenMoveIsUsedOnEmptyTileThenThrowEmptyPositionException() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();

        Board board = new Board(map);
        assertThrows(Hive.IllegalMove.class, () -> {
            board.moveTile(new Position(0,0), new Position(0,1));
        });
    }

    @Test
    @Tag("2a")
    public void whenMoveIsUsedThenTilePositionChanged() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();
        Tile tile = mock(Beetle.class);
        Position oldPosition = new Position(0, 0);
        Position newPosition = new Position(0, 1);
        Stack<Tile> tiles = new Stack<>();
        tiles.push(tile);
        map.put(oldPosition, tiles);
        Board board = new Board(map);
        HashSet<Position> positions = new HashSet<>(oldPosition.getSurroundingPositions());
        System.out.println(positions);
        when(tile.getPossibleMoves(any(Board.class), any(Position.class))).thenReturn(positions);
        try {
            board.moveTile(oldPosition, newPosition);
            Stack<Tile> movedTile = map.get(newPosition);
            assertEquals(tile, movedTile.peek());
        } catch (Hive.IllegalMove e) {
            fail("Exception was thrown: " + e.getMessage());
        }
    }

    @Test
    @Tag("2d")
    public void whenTileAlreadyPlayedThenThrowIllegalPositionException(){
        Board b = new Board();
        Tile t = new QueenBee(Hive.Player.BLACK);
        Position p1 = new Position(0, 0);
        Position p2 = new Position(1,1);

        try {
            b.putTile(p1, t);
        } catch (Hive.IllegalMove e) {
            fail("Tile could not be placed. " + e.getMessage());
        }

        assertThrows(Hive.IllegalMove.class, () -> b.putTile(p2, t));

    }

    @Test
    @Tag("2d")
    public void whenTwoDifferentTilesPlayedThenNoException(){
        Board b = new Board();
        Tile t1 = new Beetle(Hive.Player.BLACK);
        Tile t2 = new Beetle(Hive.Player.BLACK);

        Position p1 = new Position(0, 0);
        Position p2 = new Position(1, 1);

        try {
            b.putTile(p1, t1);
        } catch (Hive.IllegalMove e) {
           fail("The first tile should be able to be placed " + e.getMessage());
        }

        try {
            b.putTile(p2, t2);
        } catch (Hive.IllegalMove e) {
           fail("The second tile should be able to be placed " + e.getMessage());
        }
    }

    @Test
    @Tag("2f")
    public void whenStonesAreOnTopOfEachotherAndPositionIsMovedThenTopOneIsMoved() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();
        Stack<Tile> startingTiles = new Stack<>();
        startingTiles.push(mock(Grasshopper.class));
        Tile topTile = mock(Beetle.class);
        Position oldPosition = new Position(0, 0);
        when(topTile.getPossibleMoves(any(Board.class), any(Position.class))).thenReturn(new HashSet<>(oldPosition.getSurroundingPositions()));
        startingTiles.push(topTile);
        map.put(oldPosition, startingTiles);
        Board board = new Board(map);

        try {
            board.moveTile(oldPosition, new Position(0, 1));
            assertEquals(topTile, map.get(new Position(0,1)).peek());
        } catch (Hive.IllegalMove e) {
            fail("Exception was thrown: " + e.getMessage());
        }
    }

    @Test
    @Tag("2f")
    public void whenStonesAreOnTopOfEachotherAndPositionIsMovedThenTilesBelowAreNotMoved() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();
        Stack<Tile> startingTiles = new Stack<>();
        Tile bottomTile = mock(Beetle.class);
        startingTiles.push(bottomTile);
        Tile topTile = mock(Grasshopper.class);
        startingTiles.push(topTile);
        Position oldPosition = new Position(0, 0);
        when(topTile.getPossibleMoves(any(Board.class), any(Position.class))).thenReturn(new HashSet<>(oldPosition.getSurroundingPositions()));
        map.put(oldPosition, startingTiles);
        Board board = new Board(map);

        try {
            board.moveTile(oldPosition, new Position(0, 1));
            assertEquals(bottomTile, map.get(oldPosition).peek());
        } catch (Exception e) {
            fail("Exception was thrown: " + e.getMessage());
        }
    }

    @Test
    public void whenAllTilesAreRemovedFromPositionThenPositionHasTileShouldReturnFalse() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();
        Stack<Tile> tiles = new Stack<>();
        Beetle beetle = mock(Beetle.class);
        tiles.push(beetle);


        Position oldPosition = new Position(0, 0);
        when(beetle.getPossibleMoves(any(Board.class), any(Position.class))).thenReturn(new HashSet<>(oldPosition.getSurroundingPositions()));
        map.put(oldPosition, tiles);
        Board board = new Board(map);

        try {
            board.moveTile(oldPosition, new Position(1, 0));
            assertFalse(board.positionHasTile(oldPosition));
        } catch (Hive.IllegalMove e) {
            fail("Exception was thrown: " + e.getMessage());
        }
    }
}
