package data.structures;


/**
 * The goal of this class is to ensure that only one instance exists at a time, while still forcing the object
 * reference to be passed around. This is to prevent multiple instances causing confusion, while ensuring that
 * the data held within the object is only given access to specific classes. A public getInstance would provide too
 * much visibility.
 *
 * This is a concept class and we're going to see how well it fits it's purpose.
 */
public abstract class PrivateSingleton {

    public abstract Class getAllowedClass();

    public PrivateSingleton(Class callingClass) throws IllegalAccessException {
        if (!getAllowedClass().equals(callingClass)) {
            throw new IllegalAccessException();
        }
    }
}
