package samples;

public class WorldObject implements World {
    private String name = "World";

    // TODO: FactoryBean 으로 생성되는 빈은 기본 생성자를 사용해야 한다.
    // 얘는 어느 타이밍에 set 해줄 수 있지?

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return name;
    }
}
