package data.structures.bag;

public class BagSingleton {
    private static Bag instance = new Bag();

    private BagSingleton() {}

    public static Bag getInstance() {
        return instance;
    }
}
