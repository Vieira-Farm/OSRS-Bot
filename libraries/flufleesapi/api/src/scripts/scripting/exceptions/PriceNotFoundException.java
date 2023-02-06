package scripts.scripting.exceptions;

public class PriceNotFoundException extends Exception {

    private final int itemId;

    public PriceNotFoundException(int itemId) {
        super();
        this.itemId = itemId;
    }

    public PriceNotFoundException(int itemId, String msg) {
        super(msg);
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }
}
