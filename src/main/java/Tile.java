import nl.hanze.hive.Hive;

import java.util.Objects;

/**
 * Equals en HashCode zijn gegenereerd door IntelliJ dus hoeven niet getest te worden.
 */
public class Tile {
    private Hive.Player player;
    private Hive.Tile tile;

    public Tile(Hive.Player player, Hive.Tile tile) {
        this.player = player;
        this.tile = tile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile1 = (Tile) o;
        return player == tile1.player &&
                tile == tile1.tile;
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, tile);
    }
}
