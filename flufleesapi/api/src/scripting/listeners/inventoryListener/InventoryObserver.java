package scripting.listeners.inventoryListener;

import org.tribot.api.General;
import org.tribot.api.ListenerManager;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.api2007.types.RSItem;
import client.clientextensions.Banking;
import scripting.exceptions.PriceNotFoundException;
import web.PriceLookup;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BooleanSupplier;

public class InventoryObserver extends Thread {
    private static final CopyOnWriteArrayList<InventoryListener> listeners = new CopyOnWriteArrayList<>();
    private HashMap<Integer, Integer> prices = new HashMap<>();
    private int gpEarned = 0;
    private BooleanSupplier condition;
    private boolean running = true, trackGP = false;
    private ListenerManager manager = new ListenerManager();

    public final BooleanSupplier DEFAULT_CONDITION = () -> {
        General.sleep(200, 400);
        return !Banking.isBankScreenOpen();
    };

    public InventoryObserver() {
        this.condition = DEFAULT_CONDITION;
    }

    public InventoryObserver(BooleanSupplier condition) {
        this.condition = condition;
    }

    public InventoryObserver(boolean trackGP) {
        this.condition = DEFAULT_CONDITION;
        this.trackGP = trackGP;
    }

    public InventoryObserver(BooleanSupplier condition, boolean trackGP) {
        this.condition = condition;
        this.trackGP = trackGP;
    }

    @Override
    public void run() {
        while (Login.getLoginState() != Login.STATE.INGAME) {
            General.sleep(500);
        }
        HashMap<Integer, Integer> map = inventoryHashMap();
        while (running) {
            if (Login.getLoginState() != Login.STATE.INGAME) {
                General.sleep(300);
                continue;
            }
            if (!condition.getAsBoolean()) {
                map = inventoryHashMap();
                General.sleep(300);
                continue;
            }
            HashMap<Integer, Integer> updatedMap = inventoryHashMap();
            for (Integer id : updatedMap.keySet()) {
                int countInitial = map.containsKey(id) ? map.get(id) : 0;
                int countFinal = updatedMap.get(id);
                if (countFinal > countInitial) {
                    addTrigger(id, countFinal - countInitial);
                } else if (countFinal < countInitial) {
                    subtractedTrigger(id, countInitial - countFinal);
                }
                map.remove(id);
            }
            for (Integer id : map.keySet()) {
                subtractedTrigger(id, map.get(id));
            }
            map = updatedMap;
            General.sleep(300);
        }
    }

    public HashMap<Integer, Integer> inventoryHashMap() {
        HashMap<Integer, Integer> map = new HashMap<>();
        RSItem[] items = Inventory.getAll();
        for (RSItem item : items) {
            map.put(item.getID(), Inventory.getCount(item.getID()));
        }
        return map;
    }

    public void addTrigger(int id, int count) {
        if (trackGP) {
            trackItem(id, count);
        }
        for (InventoryListener listener : listeners) {
            listener.inventoryItemGained(id, count);
        }
    }

    public void subtractedTrigger(int id, int count) {
        if (trackGP) {
            trackItem(id, -count);
        }
        for (InventoryListener listener : listeners) {
            listener.inventoryItemLost(id, count);
        }
    }

    public BooleanSupplier getCondition() {
        return condition;
    }

    public void setCondition(BooleanSupplier condition) {
        this.condition = condition;
    }

    private void trackItem(int id, int count) {
        if (!prices.containsKey(id)) {
            try {
                prices.put(id, PriceLookup.getPrice(id));
            } catch (PriceNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (prices.containsKey(id)) {
            gpEarned += count * prices.get(id);
        }
    }

    public int getGpEarned() {
        return gpEarned;
    }

    public void end() {
        this.running = false;
    }

    public static void addListener(InventoryListener listener) {
        listeners.addIfAbsent(listener);
    }

    public static void removeListener(InventoryListener listener) {
        listeners.remove(listener);
    }
}