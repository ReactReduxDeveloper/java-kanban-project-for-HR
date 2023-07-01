package kanban.inMemoryTaskManager.interfaces;

import kanban.model.Task;

import java.util.LinkedList;
import java.util.List;

public interface HistoryManager {

    void addHistory(Task task);

    void remove(int id);

    List<Task> getHistory();
}
