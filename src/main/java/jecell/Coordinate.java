package jecell;

public record Coordinate(int row, int column) {

    @Override
    public String toString() {
        return "(" + row + "," + column + ")";
    }
}
