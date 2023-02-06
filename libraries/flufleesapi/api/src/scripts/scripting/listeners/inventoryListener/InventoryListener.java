package scripts.scripting.listeners.inventoryListener;

public interface InventoryListener {
    void inventoryItemGained(int id, int count);
    void inventoryItemLost(int id, int count);
}
