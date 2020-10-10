package unsw.venues;

public class Room {
    private String id;
    private Size size;

    public Room(String id, Size size) {
        this.id = id;
        this.size = size;
    }

    public String getID() {
        return id;
    }

    public static Size getSize(String size) {
        return Size.check(size);
    }
    public boolean isSize(String size) {
        return this.size.getValue().toLowerCase().equals(size.toLowerCase());
    }

}