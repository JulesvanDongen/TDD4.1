import nl.hanze.hive.Hive;

import java.util.*;

class Player {

    private List<Tile> tiles;
    private Hive.Player color;

    public Player(Hive.Player color) {
        this.color = color;
        tiles = initTiles();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(tiles, player.tiles) &&
                color == player.color;
    }

    public boolean hasQueenBee() {
        for (Tile tile : tiles) {
            if (tile.getClass().equals(QueenBee.class)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tiles, color);
    }

    public Hive.Player getColor() {
        return color;
    }
}
