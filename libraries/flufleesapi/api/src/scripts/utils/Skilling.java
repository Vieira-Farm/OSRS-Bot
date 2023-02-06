package scripts.utils;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Magic;
import org.tribot.api2007.Player;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.*;
import scripts.client.clientextensions.*;

/**
 * Created by Fluffee on 16/05/2017.
 */
@SuppressWarnings("unused")
public class Skilling {

    public enum RockType {
        CLAY(6705, 434),
        COPPER(10079),
        TIN(10080),
        IRON(2576, 440),
        SILVER(7366, 442),
        COAL(7580, 453),
        GOLD(8885, 444),
        MITHRIL(-22239, 447),
        ADAMANTITE(21662, 449),
        RUNITE(-31437, 451);

        private int[] rockIds;

        RockType(int... rockIds) {
            this.rockIds = rockIds;
        }

        public int[] getItemId() {
            return rockIds;
        }
    }

    public enum Bars {
        BRONZE("Copper ore", "Tin ore", 1, 1),
        IRON("Iron ore", 1),
        STEEL("Iron ore", "Coal", 1, 2),
        SILVER("Silver ore", 1),
        GOLD("Gold ore", 1),
        MITHRIL("Mithril ore", "Coal", 1, 4),
        ADAMANTITE("Adamantite ore", "Coal", 1, 6),
        RUNITE("Runite ore", "Coal", 1, 8);

        private String material1;
        private String material2;
        private int amount1;
        private int amount2;

        Bars(String material1, String material2, int amount1, int amount2) {
            this.material1 = material1;
            this.material2 = material2;
            this.amount1 = amount1;
            this.amount2 = amount2;
        }

        Bars(String material1, int amount1) {
            this.material1 = material1;
            this.amount1 = amount1;
        }

        public String getMaterial1() {
            return material1;
        }

        public String getMaterial2() {
            return material2;
        }

        public int getAmount1() {
            return amount1;
        }

        public int getAmount2() {
            return amount2;
        }
    }

    public enum SmithingItems {
        DAGGER(9), AXE(14), CHAIN_BODY(17), MEDIUM_HELM(24), DART_TIPS(30), SWORD(10),
        MACE(15), PLATE_LEGS(20), FULL_HELM(25), ARROWTIPS(31), SCIMITAR(11), WARHAMMER(16),
        PLATE_SKIRT(21), SQUARE_SHIELD(26), KNIVES(32), LONG_SWORD(12), BATTLE_AXE(17),
        PLATE_BODY(22), KITE_SHIELD(27), BRONZE_WIRE(33), TWO_HANDED_SWORD(13), NAILS(23);

        private int interfaceID;

        SmithingItems(int interfaceID) {
            this.interfaceID = interfaceID;
        }

        public int getInterfaceID() {
            return interfaceID;
        }

    }

    /**
     * Cut's tree, detects closest tree, walks and then clicks chop. For use with ABC2 implementation.
     *
     * @param tree - RSObject that contains the tree to cut.
     * @return - True if player begins cutting tree, false otherwise.
     */
    public static boolean cutTreeAntiban(RSObject tree) {
        RSObjectDefinition definition = tree.getDefinition();
        if (definition == null) {
            return false;
        }
        String treeName = tree.getDefinition().getName();
        String[] treeOptions = {"Cut-down " + treeName, "Chop down " + treeName, "Cut down " + treeName, "Chop-down " + treeName, "Chop " + treeName, "Cut " + treeName};
        if (tree.isOnScreen() && tree.isClickable() && Clicking.click(treeOptions, tree)) {
            return Timing.waitCondition(() -> {
            General.sleep(200, 400);
            return Player.getAnimation() == 879;
        }, General.random(8000, 10000));
        } else {
            tree.adjustCameraTo();
        }
        return false;
    }

    /**
     * Clicks on an Anvil, for use with ABC2 implementation.
     *
     * @param anvil - Anvil to use as RSObject. Should be found with Antiban class
     * @return - True if successfully clicks Anvil, false otherwise.
     */
    public static boolean useAnvilForAntiban(RSObject anvil) {
        String[] smithOptions = {"smith", "Smith"};
        if (anvil.isOnScreen() && anvil.isClickable() && Clicking.click(smithOptions, anvil)) {
            Timing.waitCondition(() -> {
                General.sleep(200, 400);
                return Interfaces.get(312) != null;
            }, General.random(10000, 15000));
        } else {
            if (!anvil.adjustCameraTo()) {
                Walking.blindWalkTo(anvil);
            }
        }
        return Interfaces.get(312) != null;
    }

    /**
     * Smiths an item at the anvil, for use with ABC2 implementation. TODO: Rewrite if making a smither.
     *
     * @param item   - Item that is to be smithed, pulled from enum
     * @param amount - Amount of the item to smith
     * @return - True if successfully started smithing, false otherwise.
     */
    public static boolean smithItemForAntiban(SmithingItems item, int amount) {
        if (Interfaces.get(312) != null) {
            RSInterfaceChild smithingItem = Interfaces.get(312, item.getInterfaceID());
            if (amount == 1) {
                smithingItem.click("Smith");
            } else if (amount == 5) {
                smithingItem.click("Smith 5");
            } else if (amount == 10) {
                smithingItem.click("Smith 10");
            } else {
                smithingItem.click("Smith");
                Timing.waitCondition(() -> {
                    General.sleep(200, 400);
                    return Interfaces.get(162, 32).getText().equals("Enter amount:");
                }, General.random(2000, 3000));
                Keyboard.typeSend(Integer.toString(amount));
            }
            Timing.waitCondition(() -> {
                General.sleep(200, 400);
                return Player.getAnimation() == 898;
            }, General.random(2000, 3000));
        }
        return Player.getAnimation() == 898;
    }

    /**
     * Smelts bar at the furnace, for use with ABC2 implementation. Primarily used for Tutorial Island. TODO: Rewrite if making a smither.
     *
     * @param bar     - Bar to create, pulled from the Bars enum
     * @param furnace - Furnace to smelt bar on as an RSObject. Ideally this would be found with the ABC2 find select object method.
     * @return
     */
    public static boolean smeltBarTutorial(Bars bar, RSObject furnace) {
        boolean material1 = Inventory.getCount(bar.getMaterial1()) >= bar.getAmount1(), material2 = bar.getMaterial2().equals(null) ? true : Inventory.getCount(bar.getMaterial2()) >= bar.getAmount2();
        if (material1 && material2) {
            if (furnace.isOnScreen() && furnace.isClickable() && Clicking.click("Use Furnace", furnace)) {
                Timing.waitCondition(() -> {
                    General.sleep(200, 400);
                    return Player.getAnimation() == 3243;
                }, General.random(2000, 3000));
            } else {
                furnace.adjustCameraTo();
            }
        }
        return Player.getAnimation() == 3243;
    }

    /**
     * Casts a combat spell on npc passed from Script.
     *
     * @param spell   - Spell name to cast, as String
     * @param monster - Monster to cast the spell on, as RSNPC
     * @return - Returns true if the cast is successful, false otherwise.
     */
    public static boolean castCombatSpellForAntiban(String spell, RSNPC monster) {
        if (Game.isUptext("Cast " + spell)) {
            if (monster.isOnScreen() && monster.isClickable()) {
                if (monster.getInteractingIndex() == -1) {
                    if (Clicking.click("Cast " + spell + " -> " + monster.getName(), monster)) {
                        Timing.waitCondition(() -> {
                            General.sleep(200, 400);
                            return Player.getRSPlayer().getInteractingIndex() != -1;
                        }, General.random(2000, 3000));
                    }
                }
            } else {
                monster.adjustCameraTo();
                return false;
            }
        } else {
            Magic.selectSpell(spell);
            return false;
        }
        return Player.getRSPlayer().getInteractingIndex() != -1;
    }

    /**
     * Range's monster from safespot.
     *
     * @param monster - RSNPC object, monster to attack. Ideally this would be found with the ABC2 find select object method.
     * @return True - If interacting with monster, false otherwise.
     */
    public static boolean rangeMonsterSafeSpotForAntiban(RSNPC monster) {
        String[] monsterOptions = {"Attack"};
        if (monster.isInCombat()) {
            return false;
        }
        if (monster.isClickable() && Clicking.click(monsterOptions, monster)) {
            return Timing.waitCondition(() -> {
                General.sleep(200, 400);
                return Player.getRSPlayer().getInteractingIndex() != -1;
            }, General.random(2000, 3000));
        } else {
            monster.adjustCameraTo();
            return false;
        }
    }

    /**
     * Melee's monster. Detects closest monster, walks and then clicks attack. For use with ABC2 implementation.
     *
     * @param monster - RSNPC object, monster to attack. Ideally this would be found with the ABC2 find select object method.
     * @return True - If interacting with monster, false otherwise.
     */
    public static boolean meleeMonsterForAntiban(RSNPC monster) {
        String[] monsterOptions = {"Attack"};
        if (monster.isOnScreen()) {
            if (monster.getInteractingIndex() == -1) {
                if (Clicking.click(monsterOptions, monster)) {
                    return Timing.waitCondition(() -> {
                        General.sleep(200, 400);
                        return Player.getRSPlayer().getInteractingIndex() != -1;
                    }, General.random(2000, 3000));
                }
            }
        } else {
            monster.adjustCameraTo();
        }
        return false;
    }

    /**
     * Mine's rock. Detects closest rock, walks and then clicks mine, for use with ABC2 implementation.
     *
     * @param rock - Rock to mine, as an RSObject. Ideally this would be found with the ABC2 find select object method.
     * @return - True if mining successfully begins, false otherwise.
     */
    public static boolean mineRockForAntiban(RSObject rock) {
        String[] rockOptions = {"Mine Rocks", "Mine", "Mine rocks"};
        if (rock.isOnScreen() && rock.isClickable() && Clicking.click(rockOptions, rock)) {
            Timing.waitCondition(() -> {
                General.sleep(200, 400);
                return Player.getAnimation() == 625;
            }, General.random(5000, 6000));
        } else {
            rock.adjustCameraTo();
        }
        return Player.getAnimation() == 625;
    }

    /**
     * Cooks food on fire, no antiban
     *
     * @param food - Food to cook
     * @param fire - fire as RSObject, fire to cook on. It does check to make sure that the fire exists
     * @return - True if cooking successfully begins, false otherwise.
     */
    public static boolean cookFireForAntiban(String food, RSObject fire) {
        if (fire != null && (Inventory.getCount(food) > 0)) {
            Utilities.openGameTab(GameTab.TABS.INVENTORY);
            if (InventoryUtil.useObject(food)) {
                if (fire.isOnScreen() && fire.isClickable() && Clicking.click("Use " + food + " -> Fire", fire)) {
                    return Timing.waitCondition(() -> {
                        General.sleep(200, 400);
                        return Player.getAnimation() == 897; //Does this animation change with different foods?
                    }, General.random(2000, 3000));
                } else {
                    fire.adjustCameraTo();
                }
            }
        }
        return false;
    }

    /**
     * Cooks gnomebowls on range, for use with Antiban
     *
     * @param food  - Food to cook
     * @param range - Range to cook gnomebowls on as an RSObject. Ideally this would be found with the ABC2 find select object method.
     * @return - True if cooking successfully begins, false otherwise.
     */
    public static boolean cookRangeForAntiban(String food, RSObject range) {
        if (range != null && Inventory.getCount(food) > 0) {
            if (InventoryUtil.useObject(food)) {
                if (!range.isOnScreen() || !range.isClickable()) {
                    range.adjustCameraTo();
                    return false;
                }
                if (Clicking.click("Use " + food + " -> Range", range)) {
                    return Timing.waitCondition(() -> {
                        General.sleep(200, 400);
                        return Player.getAnimation() == 896; //Does this animation change with fishing styles?
                    }, General.random(2000, 3000));
                } else {
                    range.adjustCameraTo();
                }
            }
        }
        return false;
    }

    public static boolean catchFishForAntiban(RSNPC fishingSpot, String action) {
        if (fishingSpot.isOnScreen() && fishingSpot.isClickable() && Clicking.click(action + " " + fishingSpot.getName(), fishingSpot)) {
            return Timing.waitCondition(() -> {
                General.sleep(200, 400);
                return Player.getAnimation() == 621; //Does this animation change with fishing styles?
            }, General.random(4000, 8000));
        } else {
            fishingSpot.adjustCameraTo();
        }
        return false;
    }


    /**
     * Checks to see if there are fires lit on the current space, moves if necessary
     * TODO: Add checking for other things that can block fires, and not just other fires themselves.
     *
     * @return - True if there are no fires lit on the current space, false otherwise.
     */
    public static boolean checkFiremaking() {
        return Objects.getAt(Player.getPosition(), Filters.Objects.nameContains("Fire")).length > 0 ||
                Objects.getAt(Player.getPosition(), Filters.Objects.nameContains("Bullrushes")).length > 0;
    }

    public static boolean makeFireABC2(String logName, boolean random) {
        String firstItem = random ? logName : "Tinderbox";
        String secondItem = random ? "Tinderbox" : logName;
        int logCount = Inventory.getCount(logName);
        if (logCount < 1 || Inventory.getCount("Tinderbox") < 1)
            return false;
        if (checkFiremaking()) {
            RSTile tile = Utilities.getRandomizedTile(Player.getPosition(), 5);
            Walking.walkTo(tile);
            Timing.waitCondition(ConditionUtilities.nearTile(2, tile), General.random(2000, 3000));
        }
        if (!InventoryUtil.isUsing(firstItem)) {
            InventoryUtil.useObject(firstItem);
        }
        if (InventoryUtil.isUsing(firstItem)) {
            if (InventoryUtil.interact(secondItem, "Use " + firstItem + " -> " + secondItem)) {
                long waitTime = System.currentTimeMillis();
                return Timing.waitCondition(() -> {
                    General.sleep(200, 400);
                    return Player.getAnimation() == 733;
                }, General.random(2000, 3000));
            }
        }
        return false;
    }
}