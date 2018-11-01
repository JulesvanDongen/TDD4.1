import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class BeetleSpec {
    @Test
    void whenBeetleBetweenTwoTilesThenMoveIsNotPossible() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();
        Stack<Tile> n1 = new Stack<>();
        n1.push(new Beetle(Hive.Player.BLACK));

        Stack<Tile> n2 = new Stack<>();
        n2.push(new Beetle(Hive.Player.BLACK));

        Stack<Tile> a = new Stack<>();
        Beetle beetle = new Beetle(Hive.Player.WHITE);
        a.push(beetle);

        map.put(new Position(0, 0), a);
        map.put(new Position(1, 0), n1);
        map.put(new Position(0, 1), n2);

        Board board = new Board(map);

        assertFalse(beetle.getPossibleMoves(board, new Position(0,0)).contains(new Position(1,1)));
    }

    @Test
    void whenBeetleOnBoardThenCanMoveOnTopOfOtherTiles() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();
        Stack<Tile> a = new Stack<>();
        a.push(new Beetle(Hive.Player.WHITE));

        Stack<Tile> b = new Stack<>();
        Beetle beetle = new Beetle(Hive.Player.BLACK);
        b.push(beetle);

        map.put(new Position(0, 0),a);
        map.put(new Position(0, 1), b);

        Board board = new Board(map);

        assertTrue(beetle.getPossibleMoves(board, new Position(0,1)).contains(new Position(0,0)));
    }
}
