import nl.hanze.hive.Hive;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Grasshopper extends Tile {

    public Grasshopper(Hive.Player player) {
        super(player, Hive.Tile.GRASSHOPPER);
    }

    @Override
    Set<Position> getPossibleMoves(Board board, Position currentPosition) {
        Set<Position> surroundingTilePositions = board.getSurroundingTiles(currentPosition).keySet();
        HashSet<Position> foundPositions = new HashSet<>();

        for (Position surroundingTilePosition : surroundingTilePositions) {
            int directionQ = surroundingTilePosition.getQ() - currentPosition.getQ();
            int directionR = surroundingTilePosition.getR() - currentPosition.getR();

            Position nextPosition = new Position(currentPosition.getQ(), currentPosition.getR());
            while (board.getInternalState().keySet().contains(nextPosition)) {
                nextPosition = new Position(nextPosition.getQ() + directionQ, nextPosition.getR() + directionR);
            }
            foundPositions.add(nextPosition);
        }

        return foundPositions;
    }
}
