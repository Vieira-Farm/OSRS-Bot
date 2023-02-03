package data;

import data.structures.PrivateSingleton;
import script.TutorialIsland;

public class Variables extends PrivateSingleton {

    private boolean pickedGender;
    private String username;

    @Override
    public Class getAllowedClass() {
        return TutorialIsland.class;
    }

    public Variables(Class callingClass) throws IllegalAccessException {
        super(callingClass);
        pickedGender = false;
    }

    public boolean hasPickedGender() {
        return pickedGender;
    }

    public void setPickedGender(boolean pickedGender) {
        this.pickedGender = pickedGender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
