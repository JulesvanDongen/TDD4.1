import nl.hanze.hive.Hive;

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

    public void putTile(Position position, Tile tile) throws Hive.IllegalMove {
        Stack<Tile> tiles;

        // controleer of de tile al is gespeeld. Je kan 1 steen niet twee keer spelen.
        for(Stack<Tile> stack : internalState.values()){
            if(stack.contains(tile)){
                throw new Hive.IllegalMove("This tile has allready been placed");
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

    public void moveTile(Position from, Position to) throws Hive.IllegalMove {
        if (!internalState.containsKey(from)) {
            throw new Hive.IllegalMove("This position does not contain a tile");
        }

        // Check if the move results in a disconnected Hive
        if (moveDisconnectsHive(internalState.keySet(), to)) {
            throw new Hive.IllegalMove("Moving this tile would disconnect the hive");
        }

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
            throw new Hive.IllegalMove();
        }
    }

    private boolean moveDisconnectsHive(Set<Position> occupiedPositions, Position positionToMoveFrom) {
        if (occupiedPositions.contains(positionToMoveFrom) && occupiedPositions.size() > 1) {
            occupiedPositions.remove(positionToMoveFrom);

            Set<Position> foundPosition = new HashSet<>();
            HashSet<Position> remainingPositions = new HashSet<>();
            remainingPositions.addAll(occupiedPositions);

            // Pick an occupied position
            Stack<Position> toSearch = new Stack<>();
            Position next = occupiedPositions.iterator().next();
            foundPosition.add(next);
            toSearch.push(next);

            // Find its occupied neighbours, repeat until you cannot find any
            while (toSearch.size() > 0) {
                Position pos = toSearch.pop();
                ArrayList<Position> surroundingPositionsList = pos.getSurroundingPositions();
                for (Position position : surroundingPositionsList) {
                    if (occupiedPositions.contains(position) && !foundPosition.contains(position)) { // Found a new neighbouring position
                        foundPosition.add(position);
                        toSearch.push(position);
                        remainingPositions.remove(position);
                    }
                }
            }

            // If there are no remaining positions, the hive is not disconnected
            return remainingPositions.size() == 0;
        }

        return false;
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