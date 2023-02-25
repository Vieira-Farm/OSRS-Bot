package scripts.tutorial.missions.combat;

import scripts.client.clientextensions.Game;
import scripts.scripting.frameworks.mission.missiontypes.TreeMission;
import scripts.scripting.frameworks.modulardecisiontree.nodes.BaseDecisionNode;
import scripts.tutorial.data.Constants;
import scripts.tutorial.missions.combat.decisionnodes.ShouldExitPit;

public class CombatMission implements TreeMission {

    //Documentation:
    //Setting 281 == 370 when we should talk to the guide first
    //Setting 281 == 390 when the equipment tab should be opened
    //Setting 281 == 400 when we should open the worn equipment interface
    //Setting 281 == 405 when we should equip our bronze dagger
    //Setting 281 == 410 when we should talk to the guide again
    //Setting 281 == 420 when we should equip our bronze sword and wooden shield
    //Setting 281 == 430 when we should open the combat style tab
    //Setting 281 == 440 when we should enter the pit to fight the rat
    //Setting 281 == 450 when we should click the rat to start a fight
    //Setting 281 == 460 when we need to kill a rat
    //Setting 281 == 470 when we should talk to the guide
    //Setting 281 == 480 when we need to equip arrows and/or a shortbow
    //Setting 281 == 490 when we need to range a rat

    @Override
    public BaseDecisionNode getTreeRoot() {
        return new ShouldExitPit();
    }

    @Override
    public String getMissionName() {
        return "Combat mission";
    }

    @Override
    public boolean isMissionValid() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 370 && !isMissionCompleted();
    }

    @Override
    public boolean isMissionCompleted() {
        return Game.getSetting(Constants.TUTORIAL_ISLAND_SETTING) >= 500;
    }
}
