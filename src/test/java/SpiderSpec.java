import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpiderSpec {
    @Test
    @Tag("10a")
    void whenSpiderMovesThenSlidesThreeTimes() {
        HashMap<Position, Stack<Tile>> map = new HashMap<>();

        Stack<Tile> a = new Stack<>();
        a.push(new Spider(Hive.Player.BLACK));
        map.put(new Position(0, 0), a);

        Stack<Tile> b = new Stack<>();
        Spider spider = new Spider(Hive.Player.WHITE);
        b.push(spider);
        Position spiderPosition = new Position(0, 1);
        map.put(spiderPosition, b);

        Board board = new Board(map);

        Set<Position> possibleMoves = spider.getPossibleMoves(board, spiderPosition);
        assertAll(() -> {
            assertEquals(1, possibleMoves.size(), "There was more than one possible move"); // There is just one option, the Position opposite to 0,0
        }, () -> {
            assertTrue(possibleMoves.contains(new Position(0,-1)), "Message");
        });
    }

    @Test
    @Tag("10b")
    @Disabled
    void whenSpiderMovedToOwnSpaceThenThrowIllegalMoveException() {

    }

    @Test
    @Tag("10c")
    @Disabled
    void whenSpiderMovedThenOnlyMovesOverEmptyTiles() {

    }

    @Test
    @Tag("10d")
    @Disabled
    void whenSpiderMovedThenCannotMoveToSpotBefore() {

    }
}
