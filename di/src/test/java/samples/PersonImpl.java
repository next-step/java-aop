package samples;

public class PersonImpl implements Person {
    private final String name;
    private final String nickname;

    public PersonImpl(String name, String nickname) {
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNickname() {
        return nickname;
    }
}
