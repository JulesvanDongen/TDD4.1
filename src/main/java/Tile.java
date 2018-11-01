import nl.hanze.hive.Hive;

import java.util.*;

/**
 * Equals en HashCode zijn gegenereerd door IntelliJ dus hoeven niet getest te worden.
 */
public abstract class Tile {
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

    abstract Set<Position> getPossibleMoves(Board board, Position currentPosition);

    public boolean canSlideTo(Position a, Position b, Board board) {
        Map<Position, Stack<Tile>> tilesSurroundingA = board.getSurroundingTiles(a);
        if (!tilesSurroundingA.containsKey(b) || a.equals(b)) {
            return false;
        }
        Map<Position, Stack<Tile>> tilesSurroundingB = board.getSurroundingTiles(b);
        HashSet<Position> nSet = new HashSet<>(tilesSurroundingA.keySet());
        nSet.retainAll(tilesSurroundingB.keySet());

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
}

