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

        // controleer of de tile al is gespeeld. Je kan 1 steen niet twee keer spelen.
        for(Stack<Tile> stack : internalState.values()){
            if(stack.contains(tile)){
                throw new IllegalPositionException("This tile has allready been placed");
            }
        }

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

    public boolean positionHasTile(Position position) {
        return internalState.keySet().contains(position);
    }

    public void moveTile(Position from, Position to) throws EmptyPositionException, ImpossibleMoveException {
        if (!internalState.containsKey(from)) {
            throw new EmptyPositionException();
        } else {
            Stack<Tile> tileStack = internalState.get(from);
            Tile movedTile = tileStack.pop();

            Set<Position> possibleMoves = movedTile.getPossibleMoves(this, from);
            if (possibleMoves.contains(to)) {


                if (tileStack.isEmpty()) {
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
            } else {
                throw new ImpossibleMoveException();
            }
        }
    }

    public boolean isTileNearHive(Position position) {
        Map<Position, Stack<Tile>> surroundingTiles = getSurroundingTiles(position);
        return !surroundingTiles.keySet().isEmpty();
    }

    public int getPositionHeight(Position position) {
        if (internalState.keySet().contains(position)) {
            return internalState.get(position).size();
        } else {
            return 0;
        }
    }
}

class ImpossibleMoveException extends Exception {
    public ImpossibleMoveException() {
        super();
    }

    public ImpossibleMoveException(String s) {
        super(s);
    }
}

class IllegalPositionException extends Exception {
    public IllegalPositionException() {
        super();
    }

    public IllegalPositionException(String s) {
        super(s);
    }
}

class EmptyPositionException extends Exception {
    public EmptyPositionException() {
        super();
    }

    public EmptyPositionException(String s) {
        super(s);
    }
}