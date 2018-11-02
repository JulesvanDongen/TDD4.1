import nl.hanze.hive.Hive;

import java.util.*;

/**
 * Equals en HashCode zijn gegenereerd door IntelliJ dus hoeven niet getest te worden.
 */
public abstract class Tile {
    private Hive.Player player;
    private Hive.Tile tile;
    /**
     * Heeft een uuid nodig omdat twee stenen met hetzelfde plaatje en dezelfde
     * kleur zijn niet per definitie gelijk omdat er meerdere hiervan in het spel
     * aanwezig zijn
     */
    private UUID uuid = UUID.randomUUID();

    protected Tile(Hive.Player player, Hive.Tile tile) {
        this.player = player;
        this.tile = tile;
    }

    public boolean sameKind(Tile t){
        return tile.equals(t.tile);
    }

    public boolean sameKind(Hive.Tile kind) {
        return tile.equals(kind);
    }

    public boolean samePlayer(Tile t) {
        return this.player.equals(t.player);
    }

    public boolean samePlayer(Hive.Player color) {
        return  this.player.equals(color);
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

    abstract Set<Position> getPossibleMoves(Board board, Position currentPosition);

    public boolean canSlideTo(Position a, Position b, Board board) {
        Map<Position, Stack<Tile>> tilesSurroundingA = board.getSurroundingTiles(a);
        if (!tilesSurroundingA.containsKey(b) || a.equals(b)) {
            return false;
        }
        Map<Position, Stack<Tile>> tilesSurroundingB = board.getSurroundingTiles(b);
        HashSet<Position> nSet = new HashSet<>(tilesSurroundingA.keySet());
        nSet.retainAll(tilesSurroundingB.keySet());

        if (!board.getInternalState().keySet().contains(b) && nSet.size() == 0) {
            return false; // The tile cannot be moved because it does not touch a tile when sliding to an empty position
        }

        return getMinStackSize(nSet, board) < Math.max(board.getInternalState().get(a).size() - 1, board.getInternalState().get(b).size());
    }

    private int getMinStackSize(Set<Position> positions, Board board) {
        int min = Integer.MAX_VALUE;
        for (Position position : positions) {
            Stack<Tile> tiles = board.getInternalState().get(position);
            if (tiles.size() < min) {
                min = tiles.size();
            }
        }

        if (min == Integer.MAX_VALUE) {
            return 0;
        } else {
            return min;
        }
    }

    @Override
    public String toString() {
        return String.format("Tile(%s, %s)", this.player, this.tile);
    }
}

