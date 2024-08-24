package samples;

public class SayCounter {
    private static int counter = 0;

    public static void count() {
        counter++;
    }

    public static int currentCount() {
        return counter;
    }
}
