package scripts.scripting.frameworks.task;

import scripts.scripting.frameworks.task.tasktypes.Task;

import java.util.*;

/**
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/TreeSet.html">java.util.TreeSet</a>
 *
 * @author Encoded
 */
public class TaskSet extends TreeSet<Task> {

    public TaskSet() {
        super(Comparator.comparing(Task::priority).thenComparing(Comparator.comparing(task -> task.getClass().getName())));
    }

    public TaskSet(Task... tasks) {
        super(Comparator.comparing(Task::priority).thenComparing(Comparator.comparing(task -> task.getClass().getName())));
        addAll(tasks);
    }

    public TaskSet(Comparator<? super Task> comparator) {
        this();
    }

    public TaskSet(Collection<? extends Task> c) {
        this(c.toArray(new Task[c.size()]));
    }

    public TaskSet(SortedSet<Task> s) {
        this(s.toArray(new Task[s.size()]));
    }

    public boolean addAll(Task... tasks) {
        return super.addAll(Arrays.asList(tasks));
    }

    public Task getValidTask() {
        for (Task task : this) {
            if (task.isValid()) {
                return task;
            }
        }
        return null;
    }

}