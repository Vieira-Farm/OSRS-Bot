package scripts.data.structures.bag;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import scripts.utils.gson.AnnotationExclusionStrategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A dynamic data bag which can be added/retrieved/deleted upon at runtime.
 *
 * @author Laniax
 */
public class Bag {

    private final ConcurrentHashMap<String, Object> values = new ConcurrentHashMap<>();

    /**
     * Returns the amount of items in the bag
     *
     * @return
     */
    public int getCount() {
        return values.size();
    }

    public Set<Map.Entry<String, Object>> getAll() {
        return values.entrySet();
    }

    /**
     * Returns if the bag is empty or not
     *
     * @return
     */
    public boolean isEmpty() {
        return getCount() == 0;
    }

    /**
     * Adds a item to the bag
     *
     * @param name
     * @param value
     * @return true is successfully added, false if the key was already present.
     */
    public boolean add(String name, Object value) {
        if (values.containsKey(name)) {
            return false;
        }

        values.put(name, value);
        return true;
    }

    public void addOrUpdate(String name, Object value) {
        if (value == null) {
            remove(name);
        } else {
            values.put(name, value);
        }
    }

    /**
     * Checks if an item is in the bag
     *
     * @param name
     * @return True if the key is in the bag, false otherwise.
     */
    public boolean contains(String name) {
        return values.containsKey(name);
    }

    public boolean containsAny(String... names) {
        for (String name : names) {
            if (this.contains(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets an item from the bag
     *
     * @param name
     * @return The object, or null if it didnt exist.
     */
    public <T> T get(String name) {
        if (!values.containsKey(name)) {
            return null;
        }

        return (T) values.get(name);
    }

    /**
     * Gets an item from the bag, if it doesn't exist this will return the defaultValue.
     *
     * @param name
     * @param defaultValue
     * @return The object in the bag, or the defaultValue if it didnt exist in the bag.
     */
    public <T> T get(String name, T defaultValue) {
        Object obj;
        return (obj = get(name)) != null ? (T) obj : defaultValue;

    }

    /**
     * Removes an item from the bag
     *
     * @param name
     * @return true if removed, false if not found.
     */
    public boolean remove(String name) {
        if (!values.containsKey(name)) {
            return false;
        }
        values.remove(name);
        return true;
    }

    public boolean isFalsey(String key) {
        Object obj = get(key);
        if (obj == null) {
            return true;
        }else if (ArrayList.class.isInstance(obj)) {
            return ((ArrayList) obj).isEmpty();
        } else if (obj.getClass().isArray()) {
            return ((Object[]) obj).length < 1;
        } else if (Boolean.class.isInstance(obj)) {
            return ((boolean) obj) == false;
        } else if (String.class.isInstance(obj)) {
            return ((String) obj).isEmpty();
        } else if (Integer.class.isInstance(obj)) {
            return ((int) obj) == 0;
        } else {
            return false;
        }
    }

    public boolean allFalsey(String... keys) {
        for (String key : keys) {
            if (!isFalsey(key)) {
                return false;
            }
        }
        return true;
    }

    public String getJson(String bagId) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        HashMap<String, Object> subBagMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            if (entry.getKey().startsWith(bagId)) {
                subBagMap.put(entry.getKey(), entry.getValue());
            }
        }
        return gson.toJson(subBagMap);
    }

    public void createJson(String bagId, File outputFile) {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .addSerializationExclusionStrategy(new AnnotationExclusionStrategy())
                .create();
        HashMap<String, Object> subBagMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            if (entry.getKey().startsWith(bagId)) {
                subBagMap.put(entry.getKey(), entry.getValue());
            }
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8)) {
            gson.toJson(subBagMap, outputStreamWriter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}