package study.cglibproxy;

public class ConstructorTarget {
    private final String name;

    public ConstructorTarget(final String name) {
        this.name = name;
    }

    public String sayName() {
        return name;
    }

    public String pingPong() {
        return "ping pong";
    }
}
