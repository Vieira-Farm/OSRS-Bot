package scripts.utils.numbers;

/**
 * A simple linear feedback shift register, to be used for generating consistent random numbers without duplicates.
 *
 * By Fluffee
 */
public class LFSR {

    private int seed, tapLocation, currentValue, width, maxValue;

    public LFSR(int seed, int tapLocation) {
        new LFSR(seed, tapLocation, getWidth(seed));
    }

    public LFSR(int seed, int tapLocation, int width) {
        this.seed = seed;
        this.currentValue = seed;
        this.tapLocation = tapLocation;
        this.width = width;
        this.maxValue = (1 << width) - 1;
    }

    /**
     * Peforms one step of the LFSR, and then returns the new integer in the register
     *
     * @return int representing the new value of the register
     */
    public int getNextInt() {
        return this.currentValue = step(this.width, this.tapLocation, this.currentValue, this.maxValue);
    }

    /**
     * Gets the int that exists in the register after n steps.
     *
     * @param n int representing the number of steps to perform
     * @return The int value in the register after n steps
     */
    public int getNextNthInt(int n) {
        for (int i = 0; i < n; i++) {
            getNextInt();
        }
        return this.currentValue;
    }

    /**
     * The main function of the LFSR which performs one step of the LFSR.
     * A single step consists of xor'ing the bit in the tap location, and the leftmost bit). Then, the leftmost bit is
     * removed from the register, and appends the result of the xor
     *
     * @param width int representing the width of the register
     * @param tapLocation int representing the location of the tap. Indices start from the right and at 1
     * @param currentValue int representing the current value in the register
     * @param maxValue int representing the maximum value the register can hold
     * @return int representing the current value in the register
     */
    private int step(int width, int tapLocation, int currentValue, int maxValue) {
        int xorResult = getNthBit(width, currentValue) ^ getNthBit(tapLocation, currentValue);
        currentValue = (currentValue << 1) & maxValue;
        //Shifts the value one to the left, and then &s it with the max value at the register width. This results in
        //the leftmost bit being removed.
        currentValue |= xorResult;
        return currentValue;
    }

    /**
     * Gets the width the register needs to be based on the value passed in.
     *
     * @param value int representing the value to get the width of
     * @return int representing the width the register must be.
     */
    private int getWidth(int value) {
        if (value == 0){
            return 0;
        }

        int highest = 1;
        if ((value & 0xffff0000) > 0) {
            highest += 16;
            value >>= 16;
        }
        if ((value & 0xff00) > 0) {
            highest += 8;
            value >>= 8;
        }
        if ((value & 0xf0) > 0) {
            highest += 4;
            value >>= 4;
        }
        if ((value & 0xC) > 0) {
            highest += 2;
            value >>= 2;
        }
        if ((value & 0x2) > 0) {
            highest += 1;
        }

        return highest;
    }

    /**
     * Gets the value of the nth bit
     *
     * @param n int representing the location of the bit to get
     * @param value int representing the value to get the bit from
     * @return int representing the value to get the bit from
     */
    private int getNthBit(int n, int value) {
        return (value >> n-1) & 1;
    }
}