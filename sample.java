import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TaskManagementSystem {

    public static void main(String[] args) {
        Task task = new Task("Complete Java project", "Finish the Java project by the end of the week.");
        task.addSubTask(new SubTask("Write unit tests", "Create unit tests for all classes."));
        task.addSubTask(new SubTask("Refactor code", "Improve code quality by refactoring."));

        System.out.println("All tasks:");
        task.displayTasks(t -> true);

        System.out.println("\nCompleted tasks:");
        task.markTaskAsCompleted("Refactor code");
        task.displayTasks(SubTask::isCompleted);
    }
}

abstract class AbstractTask {

    private String title;
    private String description;
    private boolean completed;

    public AbstractTask(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markAsCompleted() {
        this.completed = true;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Description: " + description + ", Completed: " + completed;
    }
}

interface TaskInterface {
    void addSubTask(SubTask subTask);
    void markTaskAsCompleted(String title);
    void displayTasks(Predicate<SubTask> filter);
}

class Task extends AbstractTask implements TaskInterface {

    private List<SubTask> subTasks;

    public Task(String title, String description) {
        super(title, description);
        this.subTasks = new ArrayList<>();
    }

    @Override
    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
    }

    @Override
    public void markTaskAsCompleted(String title) {
        subTasks.stream()
                .filter(subTask -> subTask.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .ifPresent(SubTask::markAsCompleted);
    }

    @Override
    public void displayTasks(Predicate<SubTask> filter) {
        System.out.println(this);
        subTasks.stream()
                .filter(filter)
                .forEach(System.out::println);
    }
}

class SubTask extends AbstractTask {

    public SubTask(String title, String description) {
        super(title, description);
    }
}