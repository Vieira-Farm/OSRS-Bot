package data.structures.bag;

/**
 * Enum containing the unique bag identifier for each mission. This ensures that each mission has unique bag variables.
 * That way a mission can remove all of it's variables, as well as not worry about clobbering another mission's
 * variables.
 *
 * Each ID is a 3 character alphanumeric string.
 */
public enum BagIds {

    ACCOUNT_CREATOR("AAA_"),
    BANKING("AAB_"),
    DROPPING("AAC_"),
    LOGIN("AAD_"),
    MULE_MASTER("AAE_"),
    MULE_SLAVE("AAF_"),
    TUTORIAL_ISLAND("AAG_"),
    RESTLESS_GHOST("AAH_"),
    ROMEO_AND_JULIET("AAI_"),
    RUNE_MYSTERIES("AAJ_"),
    BANK_CRAFTING("AAK_"),
    FURNACE_CRAFTING("AAL_"),
    FIREMAKING("AAM_"),
    WOODCUTTING("AAN_"),
    PROJECT_WORLD("AAO_"),
    PROJECT_WORLD_ACCOUNT_CREATION("AAP_"),
    PROJECT_WORLD_RESTLESS_GHOST("AAQ_"),
    LOGOUT("AAR_"),
    ACCOUNT_SAVER("AAS_"),
    CRAFTING_AIO("AAT_"),
    ;

    private final String id;

    BagIds(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
