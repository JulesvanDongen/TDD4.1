import nl.hanze.hive.Hive;

import java.util.ArrayList;
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

    public Tile playTile(Hive.Tile kind) throws NoSuchTileException{
        Tile tile = tiles.stream()
                .filter(t -> t.sameKind(kind))
                .findFirst()
                .orElseThrow(() -> new NoSuchTileException("The tile of kind " + kind + "does not exist"));

        tiles.remove(tile);
        return tile;
    }

                         /**
     * @return A view of the tiles this player still has available
     */
    public List<Tile> availableTiles() {
        return Collections.unmodifiableList(tiles);
    }

    private List<Tile> initTiles() {
        Tile[] startTiles = {
                new QueenBee(color),
                new Spider(color),
                new Spider(color),
                new Beetle(color),
                new Beetle(color),
                new SoldierAnt(color),
                new SoldierAnt(color),
                new SoldierAnt(color),
                new Grasshopper(color),
                new Grasshopper(color),
                new Grasshopper(color),

        };
        return new ArrayList<>(Arrays.asList(startTiles));
    }

}
