import nl.hanze.hive.Hive;

/**
 * Houdt staat bij van wie er aan de beurt is en welke stenen elke speler heeft
 */
class HiveGame {
    private Board board;

    public HiveGame(Board board) {
        this.board = board;
    }

    public void play(Hive.Tile tile, int q, int r) throws Hive.IllegalMove {

    }

    public void move(int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {

    }

    public void pass() throws Hive.IllegalMove {

    }

    public boolean isWinner(Hive.Player player) {
        return false;
    }

    public boolean isDraw() {
        return false;
    }
}
