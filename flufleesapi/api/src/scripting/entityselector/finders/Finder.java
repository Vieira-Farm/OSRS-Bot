package scripting.entityselector.finders;

import org.tribot.api.interfaces.Clickable07;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Laniax
 */
public abstract class Finder<T extends Clickable07, S> extends FinderResult<T> implements Supplier<S> {

    protected final List<Predicate<T>> filters;

    protected Finder() {
        filters = new ArrayList<>();
    }

    protected Predicate<T> buildFilter() {
        Predicate<T> result = null;

        for (Predicate<T> filter : filters) {

            if (result == null) {
                result = filter;
                continue;
            }

            result = result.and(filter);
        }

        return result;
    }

    /**
     * Apply a lambda as a custom filter. Example usage:
     * {@code .custom((obj) -> obj.isOnScreen()}
     * Which would only returns obj's that are on screen.
     * @param lambda <T> the lambda to execute
     * @return
     */
    @SuppressWarnings("unchecked")
    public S custom(Predicate<T> lambda) {

        filters.add(t -> lambda.test(t));

        return (S)this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public S get() {
        return (S)this;
    }

}
