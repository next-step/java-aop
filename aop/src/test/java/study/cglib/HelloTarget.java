package study.cglib;

class HelloTarget {
    public String sayHello(final String name) {
        return "Hello " + name;
    }

    public String sayHi(final String name) {
        return "Hi " + name;
    }

    public String sayThankYou(final String name) {
        return "Thank You " + name;
    }

    public String pingpong(final String name) {
        return "Pong " + name;
    }
}
