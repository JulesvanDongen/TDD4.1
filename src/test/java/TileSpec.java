import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class TileSpec {
    @Test
    @Disabled
    void whenTileOnBoardThenTileCannotSlideBetweenOtherTiles() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();
        Stack<Tile> tiles00 = new Stack<>();
        Beetle beetle = new Beetle(Hive.Player.WHITE);
        tiles00.push(beetle);

        Stack<Tile> tiles01 = new Stack<>();
        Beetle beetle1 = new Beetle(Hive.Player.WHITE);
        tiles01.push(beetle1);

        Stack<Tile> tiles10 = new Stack<>();
        Beetle beetle2 = new Beetle(Hive.Player.WHITE);
        tiles10.push(beetle2);

        map.put(new Position(0, 0), tiles00);
        map.put(new Position(0, 1), tiles01);
        map.put(new Position(1, 0), tiles10);

        Board board = new Board(map);

        assertThrows(ImpossibleMoveException.class, () -> {
            board.moveTile(new Position(0,0), new Position(1,1));
        });
    }
}
