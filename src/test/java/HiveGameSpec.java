import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class HiveGameSpec {

    @Test
    @Tag("4a")
    public void whenGameStartedThenTurnToWhite() {
        HiveGame game = new HiveGame(new Board());

        assertEquals(game.getTurn().getColor(), Hive.Player.WHITE);
    }

    @Test
    @Tag("3b")
    public void whenPassThenOpponentsTurn() {
        HiveGame game = new HiveGame(new Board());

        Player p1 = game.getTurn();

        try {
            game.play(Hive.Tile.BEETLE, 0, 0);
        } catch (Hive.IllegalMove illegalMove) {
            fail("First tile should be able to be played");
        }

        Player p2 = game.getTurn();
        assertFalse(p1 == p2);
    }

    @Test
    @Tag("3b")
    public void whenTilePlayedThenOpponentsTurn() {
        HiveGame game = new HiveGame(new Board());

        Player p1 = game.getTurn();
        try {
            game.play(Hive.Tile.SOLDIER_ANT, 1,1);
        } catch (Hive.IllegalMove illegalMove) {
            fail("this method should be allowed");
        }

        Player p2 = game.getTurn();

        assertFalse(p1 == p2);
    }

    @Test
    @Tag("3b")
    public void whenTileMovedThenOpponentsTurn() {
        HiveGame game = new HiveGame(new Board());
        Player p1 = game.getTurn();
        Player p2 = null;

        try {
            game.play(Hive.Tile.BEETLE, 1,1);
            p2 = game.getTurn();
            game.play(Hive.Tile.SPIDER, 0, 0);
            game.move(1,1, 1,0);
        } catch (Hive.IllegalMove illegalMove) {
            fail(illegalMove);
        }

        assertFalse(p2 == p1 && p2 != null);
    }

    @Test
    @Tag("3c")
    public void whenQueenSurroundedByOpponentThenOpponentWins() {
        Board b = new Board();

        Position queenPos = new Position(0, 0);
        try {
            b.putTile(queenPos, new QueenBee(Hive.Player.WHITE));
        } catch (IllegalPositionException e) {
            e.printStackTrace();
        }

        //surround whit queen whith black grasshoppers
        try {
            for (Position p : queenPos.getSurroundingPositions()) {
                b.putTile(p, new Grasshopper(Hive.Player.BLACK));
            }
        } catch (IllegalPositionException e) {
            e.printStackTrace();
        }


        HiveGame game = new HiveGame(b);

        // In this case black should have won
        assertTrue(game.isWinner(Hive.Player.BLACK));
    }


}