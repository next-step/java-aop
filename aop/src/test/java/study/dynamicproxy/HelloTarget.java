package study.dynamicproxy;

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

    public String pingPong(String name) {
        return "Ping Pong " + name;
    }

    public String doSay(final String name) {
        return name;
    }

    public String doSayYesOrNo() {
        return "yes";
    }
}
