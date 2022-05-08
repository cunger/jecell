package examples.lab;

public record Section(

    String crayfishId,
    int speed
) {
    public static Section unoccupied() {
        return new Section(null, 0);
    }

    public static Section occupied(String id, int speed) {
        return new Section(id, speed);
    }

    public boolean isOccupied() {
        return crayfishId != null;
    }
}
