package scripts.data.accounts;

import org.tribot.api.General;

/**
 * Wrapper class that holds the creation details for accounts.
 */
public class AccountCreationDetails {

    private int creationDay, creationMonth, creationYear, creationIndex = 1;

    /**
     * Constructor to create an AccountCreationDetails object
     *
     * @param creationDay int representing the day to use as the account's birth day
     * @param creationMonth int representing the day to use as the account's birth month
     * @param creationYear int representing the day to use as the account's birth year
     * @param creationIndex int representing the number this account is in the creation process
     */
    public AccountCreationDetails(int creationDay, int creationMonth, int creationYear, int creationIndex) {
        this.creationDay = creationDay;
        this.creationMonth = creationMonth;
        this.creationYear = creationYear;
        this.creationIndex = creationIndex;
    }

    /**
     * Generates a random set of creation details.
     *
     * @param creationIndex int representing the number this account is in the creation process
     * @return AccountCreationDetails object
     */
    public static AccountCreationDetails randomFactory(int creationIndex) {
        return new AccountCreationDetails(
                General.random(1, 28), //Random day
                General.random(1, 12), //Random month
                General.random(1980, 2006), //Random year
                creationIndex
        );
    }

    /**
     * Gets the creation day value
     *
     * @return int representing the day to use as the account's birth day
     */
    public int getCreationDay() {
        return creationDay;
    }

    /**
     * Sets the day to mark as the account's birth day
     *
     * @param creationDay int representing the day to use as the account's birth day
     */
    public void setCreationDay(int creationDay) {
        this.creationDay = creationDay;
    }

    /**
     * Gets the creation month value
     *
     * @return int representing the day to use as the account's birth month
     */
    public int getCreationMonth() {
        return creationMonth;
    }

    /**
     * Sets the day to mark as the account's birth month
     *
     * @param creationMonth int representing the day to use as the account's birth month
     */
    public void setCreationMonth(int creationMonth) {
        this.creationMonth = creationMonth;
    }

    /**
     * Gets the creation year value
     *
     * @return int representing the day to use as the account's birth year
     */
    public int getCreationYear() {
        return creationYear;
    }

    /**
     * Sets the day to mark as the account's birth year
     *
     * @param creationYear int representing the day to use as the account's birth year
     */
    public void setCreationYear(int creationYear) {
        this.creationYear = creationYear;
    }

    /**
     * Gets the account creation index
     *
     * @return int representing the number this account is in the creation process
     */
    public int getCreationIndex() {
        return creationIndex;
    }

    /**
     * Sets the account creation index
     *
     * @param creationIndex int representing the number this account is in the creation process
     */
    public void setCreationIndex(int creationIndex) {
        this.creationIndex = creationIndex;
    }
}
