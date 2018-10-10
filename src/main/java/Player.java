import nl.hanze.hive.Hive;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Player {

    private List<Tile> tiles;
    private Hive.Player color;

    public Player(Hive.Player color) {
        tiles = initTiles();
        this.color = color;
    }

    /**
     * @return A view of the tiles this player still has available
     */
    public List<Tile> availableTiles() {
        return Collections.unmodifiableList(tiles);
    }

    private List<Tile> initTiles() {
        Tile[] startTiles = {
                new Tile(color, Hive.Tile.QUEEN_BEE),
                new Tile(color, Hive.Tile.SPIDER),
                new Tile(color, Hive.Tile.SPIDER),
                new Tile(color, Hive.Tile.BEETLE),
                new Tile(color, Hive.Tile.BEETLE),
                new Tile(color, Hive.Tile.SOLDIER_ANT),
                new Tile(color, Hive.Tile.SOLDIER_ANT),
                new Tile(color, Hive.Tile.SOLDIER_ANT),
                new Tile(color, Hive.Tile.SPIDER),
                new Tile(color, Hive.Tile.SPIDER),
                new Tile(color, Hive.Tile.SPIDER),

        };
        return Arrays.asList(startTiles);
    }

}
