import nl.hanze.hive.Hive;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Houdt staat bij van wie er aan de beurt is en welke stenen elke speler heeft
 */
class HiveGame {
    private Board board;
    private Player currentPlayer;
    private Player p1;
    private Player p2;


    public HiveGame(Board board) {
        this(board, new Player(Hive.Player.WHITE), new Player(Hive.Player.BLACK));
    }

    public HiveGame(Board board, Player player1, Player player2) {
        this.board = board;
        this.p1 = player1;
        this.p2 = player2;
        this.currentPlayer = p1;
    }

    public void play(Hive.Tile tile, int q, int r) throws Hive.IllegalMove {
        Position toPos = new Position(q, r);

        try {
            Stack<Tile> tilesAt = board.getInternalState().getOrDefault(toPos, new Stack<>());
            boolean isEmptyPos = tilesAt.isEmpty();
            boolean isAdjacentToHive = (board.isTileNearHive(toPos) || board.getInternalState().isEmpty()); // toPos nearHive, unless board is empty

            if(isEmptyPos &&
                isAdjacentToHive)
            {
                Tile toPlay = currentPlayer.playTile(tile);
                board.putTile(toPos, toPlay);
            }else {
                throw new Hive.IllegalMove("This position is invalid");
            }

        } catch (NoSuchTileException e) {
            throw new Hive.IllegalMove("Player has no such tile");
        }
        switchTurns();
    }

    public void move(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        if (currentPlayer.hasQueenBee()) {
            throw new Hive.IllegalMove("You need to play your Queen Bee before you can move your tiles.");
        }

        Tile topTile = board.getInternalState().get(new Position(fromQ, fromR)).peek();
        if (!topTile.samePlayer(currentPlayer.getColor())) {
            throw new Hive.IllegalMove("You cannot move your opponent's tiles.");
        }

        board.moveTile(new Position(fromQ, fromR), new Position(toQ, toR));
        switchTurns();
    }

    public void pass() throws Hive.IllegalMove {
        switchTurns();
    }

    private void switchTurns(){
        if(p1 == currentPlayer){
            currentPlayer = p2;
        } else {
            currentPlayer = p1;
        }
    }

    public boolean isWinner(Hive.Player player) {
        boolean isWin = false;
        Set<Position> allOccupiedPositions = board.getInternalState().keySet();

        Predicate<Position> positionHasQueen = p -> {
            return board.getInternalState().get(p)
                        .stream()
                        .anyMatch(t -> t.sameKind(Hive.Tile.QUEEN_BEE) && ! t.samePlayer(player));
        };

        Position queenPos = allOccupiedPositions.stream()
                                .filter(positionHasQueen) // The position of the queen of player if any
                                .findAny()
                                .orElse(null);

        // if queen present, check if surrounded by opponent
        if (queenPos != null) {
            List<Position> positionsAroundQueen = queenPos.getSurroundingPositions();
            List<Tile> tilesAroundQueen = positionsAroundQueen.stream()
                    .map(p -> board.getInternalState().get(p)) // Stream<Stack<Tile>>
                    .filter(stack -> stack != null)
                    .map(ArrayList::new)                       // Stream<List<Tile>>
                    .flatMap(List::stream)                     // Stream<Tile> -> all tiles surrounding queen
                    .collect(Collectors.toList());

            isWin = tilesAroundQueen.size() == 6 && tilesAroundQueen.stream().allMatch(t -> t.samePlayer(player));
        }

        return isWin;
    }

    public boolean isDraw() {
        boolean whiteWins = isWinner(Hive.Player.WHITE);
        boolean blackWins = isWinner(Hive.Player.BLACK);
        return blackWins && whiteWins;
    }

    public Player getTurn() {
        return currentPlayer;
    }
}
