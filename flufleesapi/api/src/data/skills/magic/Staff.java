package data.skills.magic;

public class Staff {

    private String staffName;
    private Runes firstRune, secondRune, thirdRune;

    public Staff(String staffName, Runes firstRune, Runes secondRune, Runes thirdRune) {
        this.staffName = staffName;
        this.firstRune = firstRune;
        this.secondRune = secondRune;
        this.thirdRune = thirdRune;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Runes getFirstRune() {
        return firstRune;
    }

    public void setFirstRune(Runes firstRune) {
        this.firstRune = firstRune;
    }

    public Runes getSecondRune() {
        return secondRune;
    }

    public void setSecondRune(Runes secondRune) {
        this.secondRune = secondRune;
    }

    public Runes getThirdRune() {
        return thirdRune;
    }

    public void setThirdRune(Runes thirdRune) {
        this.thirdRune = thirdRune;
    }
}
