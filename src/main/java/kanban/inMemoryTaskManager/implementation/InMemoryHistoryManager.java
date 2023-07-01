package kanban.inMemoryTaskManager.implementation;

import kanban.inMemoryTaskManager.interfaces.HistoryManager;
import kanban.model.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedList<Task> requestHistory;
    private static final int HISTORY_SIZE = 10;

    public InMemoryHistoryManager() {
        this.requestHistory = new LinkedList<>();
    }

    @Override
    public List<Task> getHistory() {
        return requestHistory;
    }

    @Override
    public void addHistory(Task task) {
        if (task != null) {
            if (requestHistory.size() == HISTORY_SIZE) {
                requestHistory.remove(0);
            }
            requestHistory.add(task);
        }
    }

}
