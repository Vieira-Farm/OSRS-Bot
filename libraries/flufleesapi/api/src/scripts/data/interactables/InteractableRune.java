package scripts.data.interactables;


import scripts.data.skills.magic.Runes;

public class InteractableRune extends InteractableItem {

    private Runes rune;

    public InteractableRune(Runes rune, int quantity) {
        super(rune.getRuneName(), "Use", DEFAULT_ID, quantity);
        this.rune = rune;
    }

    public InteractableRune(Runes rune, int quantity, int interactableID) {
        super(rune.getRuneName(), "Use", interactableID, quantity);
        this.rune = rune;
    }

    public Runes getRune() {
        return rune;
    }
}
