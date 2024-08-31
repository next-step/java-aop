package samples;

public class WorldObject implements World {
    private final String name;

    public WorldObject(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return name;
    }
}
