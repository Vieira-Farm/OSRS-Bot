package scripts.utils.numbers;

public class NumberManipulation {

    public static int concatenate(int a, int b) {
        if ( b == 0 ) {
            a *= 10;
        } else {
            int tempB = b;
            while ( tempB > 0 ) {
                tempB /= 10;
                a *= 10;
            }
        }
        return a + b;
    }

    public static byte concatenate(byte a, byte b) {
        if ( b == 0 ) {
            a *= 10;
        } else {
            byte tempB = b;
            while ( tempB > 0 ) {
                tempB /= 10;
                a *= 10;
            }
        }
        return (byte) (a + b);
    }
}
