package scripts.data.interactables;

public class Interactable extends BaseInteractable {

    protected String action, name;
    protected int numberFoundIds, numberIds;
    protected int[] ids;

    /**
     * Constructor for an interactable object that has a single id
     *
     * @param name Name of object
     * @param action Action to use with the object (i.e. Wield, Wear, etc.)
     * @param id ID of the object that we know starting off.
     */
    public Interactable(String name, String action, int id) {
        this.name = name;
        this.action = action;
        this.ids = new int[]{id};
    }

    /**
     * Create an interactable object with multiple ids.
     *
     * @param name Name of object
     * @param numberIds Number of possible IDs the object can have. This is necessary to allow the interactable to know
     *                  when to stop searching.
     * @param action Action to use with the object (i.e. Wield, Wear, etc.)
     * @param ids IDs of the object that we know starting off.
     */
    public Interactable(String name, String action, int numberIds, int... ids) {
        this.name = name;
        this.action = action;
        this.numberIds = numberIds;
        this.numberFoundIds = ids.length;

        if (numberIds != ids.length) {
            this.ids = new int[numberIds];
            for (int i = ids.length; i < numberIds; i++) {
                this.ids[i] = DEFAULT_ID;
            }
        } else {
            this.ids = ids;
        }
    }

    /**
     * Returns the first id cached for the interactable
     *
     * @return int containing the id.
     */
    @Override
    public int getId() {
        return ids[0];
    }

    /**
     * Gets a specific ID from the stored ID array
     *
     * @param index index of the id to grab, indices start at 0.
     * @return int containing the specified id.
     */
    public int getId(int index) {
        return ids[index];
    }

    /**
     *
     * @param id
     */
    @Override
    public void setId(int id) {
        if (this.ids.length < this.numberFoundIds) {
            this.ids[this.numberFoundIds++] = id;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isAllIdsFound() {
        return this.numberFoundIds == this.ids.length;
    }

    public String getAction() {
        return action;
    }

    public int getNumberIds() {
        return numberIds;
    }

    public int[] getInteractableIds() {
        return ids;
    }
}
