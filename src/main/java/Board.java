import java.util.*;

public class Board {
    private Map<Position, Stack<Tile>> internalState;

    public Board(Map<Position, Stack<Tile>> internalState) {
        this.internalState = internalState;
    }

    public Board() {
        this.internalState = new HashMap<>();
    }

    public Map<Position, Stack<Tile>> getInternalState() {
        return internalState;
    }

    public void setInternalState(Map<Position, Stack<Tile>> internalState) {
        this.internalState = internalState;
    }

    public void putTile(Position position, Tile tile) throws IllegalPositionException {
        Stack<Tile> tiles;
        if (!internalState.containsKey(position)) {
            tiles = new Stack<>();
            internalState.put(position, tiles);
        } else {
            tiles = internalState.get(position);
        }
        tiles.push(tile);
    }

    public Map<Position, Stack<Tile>> getSurroundingTiles(Position position) {
        Map<Position, Stack<Tile>> surroundingTiles = new HashMap<>();

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
            Stack<Tile> tileStack = internalState.get(from);
            Tile movedTile = tileStack.pop();

            if (internalState.isEmpty()) {
                internalState.remove(from);
            }

            if (internalState.containsKey(to)) {
                Stack<Tile> tiles = internalState.get(to);
                tiles.push(movedTile);
            } else {
                Stack<Tile> stack = new Stack<>();
                stack.push(movedTile);
                internalState.put(to, stack);

            }
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