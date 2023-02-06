package scripts.data.skills.magic;

public class SpellRune {

    private Runes rune;
    private int runeCount;

    public SpellRune(Runes rune, int runeCount) {
        this.rune = rune;
        this.runeCount = runeCount;
    }

    public int getRuneCount() {
        return runeCount;
    }

    public Runes getRune() {
        return rune;
    }
}
