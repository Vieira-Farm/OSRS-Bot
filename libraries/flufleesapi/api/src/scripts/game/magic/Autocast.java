package scripts.game.magic;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Game;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Login;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSInterfaceComponent;
import scripts.data.skills.magic.Spell;
import scripts.utils.ConditionUtilities;

public class Autocast {

    public static final int AUTOCAST_GAME_SETTING = 108;
    public static final int AUTOCAST_SELECTION_MASTER = 201;
    public static final int AUTOCAST_SELECTION_CHILD = 1;
    public static final int COMBAT_TAB_MASTER = 593;
    public static final int AUTOCAST_BUTTON = 24;
    public static final int AUTOCAST_DEFENSIVE_BUTTON = 20;

    public static boolean isAutocastEnabled(Spell spell) {
        return spell.getAutocastSetting() == Game.getSetting(AUTOCAST_GAME_SETTING);
    }

    public static boolean enableAutocast(Spell spell, boolean defensive) {
        if (!autocastSelectionOpened())
            if (!openAutocastSelection(defensive))
                return false;
        RSInterfaceChild autocastSelection = Interfaces.get(AUTOCAST_SELECTION_MASTER, AUTOCAST_SELECTION_CHILD);
        if (autocastSelection == null)
            return false;
        RSInterfaceComponent spellToSelect = autocastSelection.getChild(spell.getAutocastComponentID());
        if (spellToSelect != null && spellToSelect.click())
            return Timing.waitCondition(ConditionUtilities.settingChanged(AUTOCAST_GAME_SETTING, spell.getAutocastSetting()), General.random(2000, 40000));
        return false;
    }

    public static boolean openAutocastSelection(boolean defensive) {
        if (Login.getLoginState() != Login.STATE.INGAME)
            return false;
        if (GameTab.getOpen() != GameTab.TABS.COMBAT) {
            GameTab.open(GameTab.TABS.COMBAT);
            Timing.waitCondition(ConditionUtilities.tabOpened(GameTab.TABS.COMBAT), General.random(2000, 4000));
        }
        if (!Interfaces.isInterfaceSubstantiated(COMBAT_TAB_MASTER))
            return false;
        RSInterfaceChild autocastButton = Interfaces.get(COMBAT_TAB_MASTER, defensive ? AUTOCAST_DEFENSIVE_BUTTON : AUTOCAST_BUTTON);
        if (autocastButton == null)
            return false;
        return autocastButton.click("Choose spell");
    }

    public static boolean autocastSelectionOpened() {
        return Interfaces.isInterfaceSubstantiated(AUTOCAST_SELECTION_MASTER);
    }

}
