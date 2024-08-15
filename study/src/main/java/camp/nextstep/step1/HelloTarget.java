package camp.nextstep.step1;

class HelloTarget implements Hello {
    public String sayHello(String name) {
        return "Hello " + name;
    }

    public String sayHi(String name) {
        return "Hi " + name;
    }

    public String sayThankYou(String name) {
        return "Thank You " + name;
    }

    @Override
    public String pingPong(String name) {
        return "Ping Pong " + name;
    }
}
