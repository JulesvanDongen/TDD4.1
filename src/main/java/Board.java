import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private Map<Position, Tile> internalState;

    public Board(HashMap<Position, Tile> internalState) {
        this.internalState = internalState;
    }

    public Board() {
        this.internalState = new HashMap<>();
    }

    public Map<Position, Tile> getInternalState() {
        return internalState;
    }

    public void setInternalState(HashMap<Position, Tile> internalState) {
        this.internalState = internalState;
    }

    public void putTile(Position position, Tile tile) throws IllegalPositionException {
        if (internalState.containsKey(position)) {
            throw new IllegalPositionException();
        } else {
            internalState.put(position, tile);
        }
    }

    public Map<Position, Tile> getSurroundingTiles(Position position) {
        HashMap<Position, Tile> surroundingTiles = new HashMap<>();

        ArrayList<Position> surroundingPositions = position.getSurroundingPositions();

        for (Position surroundingPosition : surroundingPositions) {
            if (internalState.containsKey(surroundingPosition)) {
                surroundingTiles.put(surroundingPosition, internalState.get(surroundingPosition));
            }
        }

        return surroundingTiles;
    }

    public void moveTile(Position from, Position to) throws EmptyPositionException {
        if (!internalState.containsKey(from)) {
            throw new EmptyPositionException();
        } else {
            Tile tile = internalState.get(from);
            internalState.remove(from);
            internalState.put(to, tile);
        }
    }
}

class IllegalPositionException extends Exception {
    public IllegalPositionException() {

    }

    public IllegalPositionException(String s) {
        super(s);
    }
}

class EmptyPositionException extends Exception {
    public EmptyPositionException() {
    }

    public EmptyPositionException(String s) {
        super(s);
    }
}