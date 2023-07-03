package kanban.model;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    private ArrayList<Integer> idSubTasks;
    public static final TaskType TYPE = TaskType.TASK;

    public Epic(String title, String description, TaskStatus status) {
        super(title, description, status);
        this.idSubTasks = new ArrayList<>();
    }

    public Epic(String title, String description) {
        super(title, description, TaskStatus.NEW);
        this.idSubTasks = new ArrayList<>();
    }


    public ArrayList<Integer> getIdSubTasks() {
        return idSubTasks;
    }

    public void setIdSubTasks(ArrayList<Integer> idSubTasks) {
        this.idSubTasks = idSubTasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Epic epic)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getIdSubTasks(), epic.getIdSubTasks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getIdSubTasks());
    }

    @Override
    public String toString() {
        return "Epic{" +
                "idSubTasks=" + idSubTasks +
                '}';
    }
}
