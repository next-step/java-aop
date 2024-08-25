package samples;

public class Hello {
    private final String name;

    private Hello(final String name) {
        this.name = name;
    }

    public static Hello from(String name) {
        System.out.printf("Hello, %s%n", name);
        return new Hello(name);
    }

    public String getName() {
        return name;
    }
}
