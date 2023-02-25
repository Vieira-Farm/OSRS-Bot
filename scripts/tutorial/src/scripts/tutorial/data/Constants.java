package scripts.tutorial.data;


import scripts.client.clientextensions.RSArea;
import org.tribot.api2007.types.RSTile;

public class Constants {
    public static final int TUTORIAL_ISLAND_SETTING = 281;
    public static final int NAME_AVAILABILITY_VARBIT = 5605;
    public static final int NAME_SET_VARBIT = 5607;
    public static final int ESC_CLOSE_VARBIT = 4681;

    public enum DepositSettings {
        NONE(0),
        INVENTORY(1),
        EQUIPMENT(2),
        BOTH(3);

        private final int depositValue;

        DepositSettings(int depositValue) {
            this.depositValue = depositValue;
        }

        public int getDepositValue() {
            return depositValue;
        }
    }

    public static final RSArea COOK_AREA = new RSArea(new RSTile[] {
            new RSTile(3073, 3092, 0), new RSTile(3078, 3092, 0),
            new RSTile(3078, 3089, 0), new RSTile(3076, 3089, 0),
            new RSTile(3076, 3087, 0), new RSTile(3079, 3087, 0),
            new RSTile(3079, 3083, 0), new RSTile(3077, 3083, 0),
            new RSTile(3077, 3082, 0), new RSTile(3075, 3082, 0),
            new RSTile(3075, 3083, 0), new RSTile(3073, 3083, 0),
            new RSTile(3073, 3087, 0), new RSTile(3074, 3087, 0),
            new RSTile(3074, 3089, 0), new RSTile(3073, 3089, 0)
    });

    public static final RSTile MINING_AREA_MIDDLE_TILE = new RSTile(3080, 9506, 0);

    public static final String[] ACCOUNT_AGE_OPTIONS = new String[]{
            "I am an experienced player.",
            "I've played in the past, but not recently.",
            "I am brand new! This is my first time here."
    };

    public static final int STYLE_INTERFACE_MASTER = 269, STYLE_ACCEPT_BUTTON = 100;

    public static final RSArea UNDERGROUND_AREA = new RSArea(
            new RSTile(3066, 9540, 0), new RSTile(3124, 9486, 0)
    );
    public static final RSArea BANK_AREA = new RSArea(new RSTile [] {
            new RSTile(3118, 3119, 0),new RSTile(3118, 3120, 0),
            new RSTile(3119, 3120, 0),new RSTile(3119, 3123, 0),
            new RSTile(3118, 3123, 0),new RSTile(3118, 3126, 0),
            new RSTile(3125, 3126, 0),new RSTile(3125, 3121, 0),
            new RSTile(3126, 3121, 0),new RSTile(3126, 3120, 0),
            new RSTile(3126, 3119, 0)
    });

    public static final RSArea BANK_INSTRUCTOR_ROOM = new RSArea(
            new RSTile(3125, 3125,  0), new RSTile(3129, 3122, 0)
    );
    public static final RSArea BANK_ACCIDENT_ROOM = new RSArea(
            new RSTile(3122, 3129, 0), new RSTile(3127, 3126)
    );
    public static final RSArea MINING_AREA = new RSArea(new RSTile [] {
            new RSTile(3071, 9504, 0),new RSTile(3071, 9511, 0),
            new RSTile(3091, 9510, 0),new RSTile(3091, 9493, 0),
            new RSTile(3071, 9493, 0)
    });

    public static final RSArea QUEST_GUIDE_AREA = new RSArea(new RSTile [] {
            new RSTile(3079, 3119, 0),new RSTile(3079, 3123, 0),
            new RSTile(3079, 3124, 0),new RSTile(3080, 3125, 0),
            new RSTile(3081, 3126, 0),new RSTile(3089, 3126, 0),
            new RSTile(3090, 3126, 0),new RSTile(3090, 3119, 0)
    });

    public static final RSArea COMBAT_PIT_AREA = new RSArea(new RSTile [] {
            new RSTile(3104, 9510, 0),new RSTile(3101, 9510, 0),
            new RSTile(3105, 9510, 0),new RSTile(3107, 9513, 0),
            new RSTile(3108, 9514, 0),new RSTile(3110, 9515, 0),
            new RSTile(3110, 9516, 0),new RSTile(3111, 9517, 0),
            new RSTile(3111, 9519, 0),new RSTile(3110, 9521, 0),
            new RSTile(3110, 9522, 0),new RSTile(3109, 9523, 0),
            new RSTile(3106, 9523, 0),new RSTile(3105, 9525, 0),
            new RSTile(3102, 9526, 0),new RSTile(3098, 9523, 0),
            new RSTile(3094, 9519, 0),new RSTile(3092, 9518, 0),
            new RSTile(3094, 9514, 0),new RSTile(3098, 9511, 0)
    });

    public static final RSArea PRAYER_AREA = new RSArea(new RSTile [] {
            new RSTile(3120, 3111, 0),new RSTile(3129, 3111, 0),
            new RSTile(3129, 3103, 0),new RSTile(3120, 3103, 0)
    });
    public static final RSArea OUTSIDE_BANK_GUIDE_AREA = new RSArea(
            new RSTile(3129, 3126, 0), new RSTile(3131, 3122, 0)
    );


}
