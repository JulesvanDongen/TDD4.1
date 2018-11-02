import nl.hanze.hive.Hive;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void whenPlayerCreatedThenPlayerHasOneQueenBee() {
        Tile queenBee = new QueenBee(Hive.Player.BLACK);
        Player p = new Player(Hive.Player.BLACK);
        List<Tile> hand = p.availableTiles();
        int amountQueens = (int) hand.stream()
                .filter(t -> t.sameKind(queenBee))
                .count();
        assertEquals(1, amountQueens);
    }

    @Test
    void whenPlayerCreatedThenPlayerHasElevenTiles() {
        Player p = new Player(Hive.Player.BLACK);
        assertEquals(11, p.availableTiles().size());
    }

    @Test
    void whenPlayerCreatedThenPlayerHasTwoSpider() {
        Tile spider = new Spider(Hive.Player.BLACK);
        Player p = new Player(Hive.Player.BLACK);
        List<Tile> hand = p.availableTiles();
        int amountSpider = (int) hand.stream()
                .filter(t -> t.sameKind(spider))
                .count();
        assertEquals(2, amountSpider);
    }

    @Test
    void whenTilePlayedThenTileRemovedFromHand() {
        Player p = new Player(Hive.Player.WHITE);
        int initGrassHopperCount = 3;
        try {
            p.playTile(Hive.Tile.GRASSHOPPER);
        } catch (NoSuchTileException e) {
            fail("Tile Should be available");
        } catch (Hive.IllegalMove illegalMove) {
            fail("");
        }

        List<Tile> availableTiles = p.availableTiles();

        int actual = (int) availableTiles.stream()
                .filter(t -> t.sameKind(Hive.Tile.GRASSHOPPER))
                .count();

        assertEquals(initGrassHopperCount - 1, actual);
    }

    @Test
    void whenQueenBeePlayedThenPlayerHasNoQueenBee() {
        Player player = new Player(Hive.Player.WHITE);
        try {
            player.playTile(Hive.Tile.QUEEN_BEE);
        } catch (NoSuchTileException e) {
            fail("The player should have a Queen Bee when it is created");
        }

        assertFalse(player.hasQueenBee());
    }

    @Test
    void whenPlayerCreatedAllTilesOwnedByPlayer() {
        Player whitPlayer = new Player(Hive.Player.WHITE);
        Player blackPlayer = new Player(Hive.Player.BLACK);

        assertAll(
                () -> whitPlayer.availableTiles().stream().allMatch(t -> t.samePlayer(Hive.Player.WHITE)),
                () -> blackPlayer.availableTiles().stream().allMatch(t -> t.samePlayer(Hive.Player.BLACK))
        );
    }

    @Test
    @Tag("4e")
    void whenQueenNotPlayedByFourthTileThenThrowIllegalMove() {
        Player p = new Player(Hive.Player.WHITE);

        try {
            p.playTile(Hive.Tile.GRASSHOPPER);
            p.playTile(Hive.Tile.GRASSHOPPER);
            p.playTile(Hive.Tile.BEETLE);
        } catch (Hive.IllegalMove illegalMove) {
            illegalMove.printStackTrace();
        } catch (NoSuchTileException e) {
            e.printStackTrace();
        }

        assertThrows(Hive.IllegalMove.class, () -> p.playTile(Hive.Tile.BEETLE));
    }

    @Test
    @Tag("4e")
    void whenQueenPlayedBeforeFourthTileThenNoExceptionOnFourthTile() {
        Player p = new Player(Hive.Player.BLACK);

        try {
            p.playTile(Hive.Tile.BEETLE);
            p.playTile(Hive.Tile.QUEEN_BEE);
            p.playTile(Hive.Tile.SPIDER);
        } catch (Hive.IllegalMove | NoSuchTileException illegalMove) {
            fail("You failed, these arr valid tiles to be played");
        }

        try {
            p.playTile(Hive.Tile.SPIDER);
        } catch (Hive.IllegalMove | NoSuchTileException illegalMove) {
            fail("Queen has been played, so this method should not throw an exception");
        }
    }

    @Test
    @Tag("4e")
    void whenQueenIsFourthTileThenNoExceptionThrown() {
        Player p = new Player(Hive.Player.BLACK);

        try {
            p.playTile(Hive.Tile.BEETLE);
            p.playTile(Hive.Tile.SPIDER);
            p.playTile(Hive.Tile.SPIDER);
        } catch (Hive.IllegalMove | NoSuchTileException illegalMove) {
            fail("You failed, these arr valid tiles to be played");
        }

        try {
            p.playTile(Hive.Tile.QUEEN_BEE);
        } catch (Hive.IllegalMove | NoSuchTileException illegalMove) {
            fail("Queen is played as fourth tile, so this method should not throw an exception");
        }
    }


}
