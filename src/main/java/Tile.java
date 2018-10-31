import nl.hanze.hive.Hive;

import java.util.Objects;
import java.util.UUID;

/**
 * Equals en HashCode zijn gegenereerd door IntelliJ dus hoeven niet getest te worden.
 */
public class Tile {
    private Hive.Player player;
    private Hive.Tile tile;
    /**
     * Heeft een uuid nodig omdat twee stenen met hetzelfde plaatje en dezelfde
     * kleur zijn niet per definitie gelijk omdat er meerdere hiervan in het spel
     * aanwezig zijn
     */
    private UUID uuid = UUID.randomUUID();

    public Tile(Hive.Player player, Hive.Tile tile) {
        this.player = player;
        this.tile = tile;
    }

    public boolean sameKind(Tile t){
        return tile.equals(t.tile);
    }

    public boolean sameKind(Hive.Tile kind) {
        return tile.equals(kind);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile1 = (Tile) o;
        return player == tile1.player &&
                tile == tile1.tile &&
                Objects.equals(uuid, tile1.uuid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(player, tile, uuid);
    }
}
