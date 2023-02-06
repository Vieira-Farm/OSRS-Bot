package scripts.data.interactables;

public abstract class BaseInteractable {

    public static final int DEFAULT_ID = -2;

    public abstract int getId();
    public abstract void setId(int id);

    public abstract String getName();
    public abstract void setName(String name);

    public abstract boolean isAllIdsFound();

    @Override
    public String toString() {
        return getName();
    }
}
