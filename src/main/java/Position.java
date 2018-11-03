import java.util.ArrayList;
import java.util.Objects;

/**
 * Dataclass om de positie bij te houden.
 *
 * Equals en HashCode methoden zijn gegenereerd door IntelliJ
 */
public class Position {
    private int q, r;

    public Position(int q, int r) {
        this.q = q;
        this.r = r;
    }

    public ArrayList<Position> getSurroundingPositions() {
        ArrayList<Position> positions = new ArrayList<>();
        positions.add(new Position(this.q, this.r - 1));
        positions.add(new Position(this.q + 1, this.r -1));
        positions.add(new Position(this.q + 1, this.r));
        positions.add(new Position(this.q, this.r + 1));
        positions.add(new Position(this.q - 1, this.r + 1));
        positions.add(new Position(this.q - 1, this.r));
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return q == position.q &&
                r == position.r;
    }

    @Override
    public int hashCode() {
        return Objects.hash(q, r);
    }

    @Override
    public String toString() {
        return "Position={"+ q + ", " + r + "}";
    }
}
