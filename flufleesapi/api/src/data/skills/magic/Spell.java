package data.skills.magic;

import data.interactables.Interactable;
import data.interactables.InteractableRune;

import java.util.Arrays;
import java.util.List;

public class Spell {

    private int levelRequired, autocastComponentID, autocastSetting;
    private boolean members;
    private List<InteractableRune> runesRequired;
    private String spellName;
    private String enumSpellName;

    public Spell(int levelRequired, int autocastComponentID, int autocastSetting, boolean members, InteractableRune[] runesRequired, String spellName, String enumSpellName) {
        this.levelRequired = levelRequired;
        this.autocastComponentID = autocastComponentID;
        this.autocastSetting = autocastSetting;
        this.members = members;
        this.runesRequired = Arrays.asList(runesRequired);
        this.spellName = spellName;
        this.enumSpellName = enumSpellName;
    }

    public int getLevelRequired() {
        return levelRequired;
    }

    public void setLevelRequired(int levelRequired) {
        this.levelRequired = levelRequired;
    }

    public int getAutocastComponentID() {
        return autocastComponentID;
    }

    public void setAutocastComponentID(int autocastComponentID) {
        this.autocastComponentID = autocastComponentID;
    }

    public int getAutocastSetting() {
        return autocastSetting;
    }

    public void setAutocastSetting(int autocastSetting) {
        this.autocastSetting = autocastSetting;
    }

    public boolean isMembers() {
        return members;
    }

    public void setMembers(boolean members) {
        this.members = members;
    }

    public InteractableRune[] getRunesRequired() {
        return runesRequired.toArray(new InteractableRune[runesRequired.size()]);
    }

    public boolean allRuneIDsFound() {
        return runesRequired.stream().allMatch(Interactable::isAllIdsFound);
    }

    public int[] getRuneIDs() {
        return runesRequired.stream().mapToInt(Interactable::getId).toArray();
    }

    public int getFirstRuneID() {
        return runesRequired.size() > 0 ? runesRequired.get(0).getId() : -1;
    }

    public String getFirstRuneName() {
        return runesRequired.size() > 0 ? runesRequired.get(0).getName() : "";
    }

    public int getSecondRuneID() {
        return runesRequired.size() > 1 ? runesRequired.get(1).getId(): -1;
    }

    public String getSecondRuneName() {
        return runesRequired.size() > 1 ? runesRequired.get(1).getName() : "";
    }

    public int getThirdRuneID() {
        return runesRequired.size() > 2 ? runesRequired.get(2).getId() : -1;
    }

    public String getThirdRuneName() {
        return runesRequired.size() > 2 ? runesRequired.get(2).getName() : "";
    }

    public String[] getRuneNames() {
        return runesRequired.stream().map(Interactable::getName).toArray(String[]::new);
    }

    public void setRunesRequired(InteractableRune[] runesRequired) {
        this.runesRequired = Arrays.asList(runesRequired);
    }

    public String getSpellName() {
        return spellName;
    }

    public void setSpellName(String spellName) {
        this.spellName = spellName;
    }

    public String getEnumSpellName() {
        return enumSpellName;
    }

    public void setEnumSpellName(String enumSpellName) {
        this.enumSpellName = enumSpellName;
    }

    @Override
    public String toString() {
        String toString = this.spellName + ", Level: " + this.levelRequired;
        for (InteractableRune spellRune : runesRequired) {
            toString = toString + ", " + spellRune.getQuantity() + " x " + spellRune.getRune().getRuneName();
        }
        return toString;
    }

    //Does not include Iban Blast, Magic Dart, Saradomin Strike", Claws of Guthix, Flames of Zamorak due to them requiring a Staff.
    //Does not include Crumble Undead as it cannot be autocast.
    public enum COMBAT_SPELLS {
        WIND_STRIKE(
                "Wind Strike",
                1,
                1,
                3,
                false,
                new InteractableRune(Runes.MIND_RUNE, 1),
                new InteractableRune(Runes.AIR_RUNE, 1),
                new InteractableRune(Runes.NONE, 0)
        ),
        WATER_STRIKE("Water Strike", 5, 2, 5, false, new InteractableRune(Runes.WATER_RUNE, 1), new InteractableRune(Runes.MIND_RUNE,1), new InteractableRune(Runes.AIR_RUNE, 1)),
        EARTH_STRIKE("Earth Strike", 9, 3, 7, false, new InteractableRune(Runes.MIND_RUNE, 1), new InteractableRune(Runes.EARTH_RUNE, 2), new InteractableRune(Runes.AIR_RUNE, 1)),
        FIRE_STRIKE("Fire Strike", 13, 4, 9, false, new InteractableRune(Runes.MIND_RUNE, 1), new InteractableRune(Runes.FIRE_RUNE, 3), new InteractableRune(Runes.AIR_RUNE, 2)),
        WIND_BOLT("Wind Bolt", 17, 5, 11, false, new InteractableRune(Runes.CHAOS_RUNE, 1), new InteractableRune(Runes.AIR_RUNE, 2), new InteractableRune(Runes.NONE, 0)),
        WATER_BOLT("Water Bolt", 23, 6, 13, false, new InteractableRune(Runes.CHAOS_RUNE, 1), new InteractableRune(Runes.WATER_RUNE, 2), new InteractableRune(Runes.AIR_RUNE, 2)),
        EARTH_BOLT("Earth Bolt", 29, 7, 15, false, new InteractableRune(Runes.CHAOS_RUNE, 1), new InteractableRune(Runes.EARTH_RUNE, 3), new InteractableRune(Runes.AIR_RUNE, 2)),
        FIRE_BOLT("Fire Bolt", 35, 8, 17, false, new InteractableRune(Runes.CHAOS_RUNE, 1), new InteractableRune(Runes.FIRE_RUNE, 4), new InteractableRune(Runes.AIR_RUNE, 3)),
        WIND_BLAST("Wind Blast", 41, 9, 19, false, new InteractableRune(Runes.DEATH_RUNE, 1), new InteractableRune(Runes.AIR_RUNE, 3), new InteractableRune(Runes.NONE, 0)),
        WATER_BLAST("Water Blast", 47, 10, 21, false, new InteractableRune(Runes.DEATH_RUNE, 1), new InteractableRune(Runes.WATER_RUNE, 3), new InteractableRune(Runes.AIR_RUNE, 3)),
        EARTH_BLAST("Earth Blast", 53, 11, 23, false, new InteractableRune(Runes.DEATH_RUNE, 1), new InteractableRune(Runes.EARTH_RUNE, 4), new InteractableRune(Runes.AIR_RUNE, 3)),
        FIRE_BLAST("Fire Blast", 59, 12, 25, false, new InteractableRune(Runes.DEATH_RUNE, 1), new InteractableRune(Runes.FIRE_RUNE, 5), new InteractableRune(Runes.AIR_RUNE, 4)),
        WIND_WAVE("Wind Wave", 62, 13, 27, true, new InteractableRune(Runes.BLOOD_RUNE, 1), new InteractableRune(Runes.AIR_RUNE, 5), new InteractableRune(Runes.NONE, 0)),
        WATER_WAVE("Water Wave", 65, 14, 29, true, new InteractableRune(Runes.BLOOD_RUNE, 1), new InteractableRune(Runes.WATER_RUNE, 7), new InteractableRune(Runes.AIR_RUNE, 5)),
        EARTH_WAVE("Earth Wave", 70, 15, 31, true, new InteractableRune(Runes.BLOOD_RUNE, 1), new InteractableRune(Runes.EARTH_RUNE, 7), new InteractableRune(Runes.AIR_RUNE, 5)),
        FIRE_WAVE("Fire Wave", 75, 16, 33, true, new InteractableRune(Runes.BLOOD_RUNE, 1), new InteractableRune(Runes.FIRE_RUNE, 7), new InteractableRune(Runes.AIR_RUNE, 5)),
        WIND_SURGE("Wind Surge", 81, 17, 35, true, new InteractableRune(Runes.WRATH_RUNE, 1), new InteractableRune(Runes.AIR_RUNE, 5), new InteractableRune(Runes.NONE, 0)),
        WATER_SURGE("Water Surge", 85, 18, 37, true, new InteractableRune(Runes.WRATH_RUNE, 1), new InteractableRune(Runes.WATER_RUNE, 10), new InteractableRune(Runes.AIR_RUNE, 7)),
        EARTH_SURGE("Earth Surge", 90, 19, 39, true, new InteractableRune(Runes.WRATH_RUNE, 1), new InteractableRune(Runes.EARTH_RUNE, 10), new InteractableRune(Runes.AIR_RUNE, 7)),
        FIRE_SURGE("Fire Surge", 95, 20, 41, true, new InteractableRune(Runes.WRATH_RUNE, 1), new InteractableRune(Runes.FIRE_RUNE, 10), new InteractableRune(Runes.AIR_RUNE, 7));

        private int levelRequired, autocastComponentID, autocastSetting;
        private boolean members;
        private InteractableRune[] runesRequired;
        private String spellName;
        private final int NUMBER_OF_RUNES_REQUIRED = 3;

        COMBAT_SPELLS(String spellName, int levelRequired, int autocastComponentID, int autocastSetting, boolean members, InteractableRune... runesRequired) {
            this.spellName = spellName;
            this.autocastComponentID = autocastComponentID;
            this.autocastSetting = autocastSetting;
            this.levelRequired = levelRequired;
            this.members = members;
            this.runesRequired = runesRequired;
        }

        public int getLevelRequired() {
            return levelRequired;
        }

        public int getAutocastComponentID() {
            return autocastComponentID;
        }

        public int getAutocastSetting() {
            return autocastSetting;
        }

        public boolean isMembers() {
            return members;
        }

        public InteractableRune[] getRunesRequired() {
            return runesRequired;
        }

        public int numberOfRunesRequired() {
            return NUMBER_OF_RUNES_REQUIRED;
        }

        public String getSpellName() {
            return spellName;
        }

        @Override
        public String toString() {
            return getSpellName();
        }
    }

}

