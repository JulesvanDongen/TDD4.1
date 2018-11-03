import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TileSpec {
    @Test
    @Tag("6b")
    void whenTileOnBoardThenTileCannotSlideBetweenOtherTiles() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();
        Stack<Tile> tiles00 = new Stack<>();
        Beetle beetle = new Beetle(Hive.Player.WHITE);
        tiles00.push(beetle);

        Stack<Tile> tiles1n1 = new Stack<>();
        Beetle beetle1 = new Beetle(Hive.Player.WHITE);
        tiles1n1.push(beetle1);

        Stack<Tile> tiles01 = new Stack<>();
        Beetle beetle2 = new Beetle(Hive.Player.WHITE);
        tiles01.push(beetle2);

        map.put(new Position(0, 0), tiles00);
        map.put(new Position(1, -1), tiles1n1);
        map.put(new Position(0, 1), tiles01);

        Board board = new Board(map);

        assertThrows(Hive.IllegalMove.class, () -> {
            board.moveTile(new Position(0,0), new Position(1,0));
        });
    }
    
    @Test
    @Tag("6c")
    void whenTileCannotTouchOtherTilesThenTileCannotSlide() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();

        Stack<Tile> a = new Stack<>();
        a.push(mock(Beetle.class));
        map.put(new Position(-1, 0), a);

        Stack<Tile> b = new Stack<>();
        b.push(mock(Beetle.class));
        map.put(new Position(1, 0), b);

        Stack<Tile> c = new Stack<>();
        Beetle beetle = new Beetle(Hive.Player.WHITE);
        c.push(beetle);
        Position posA = new Position(-1, 1);
        map.put(posA, c);

        Board board = new Board(map);

        beetle.canSlideTo(posA, new Position(0, 1), board);
    }
}
