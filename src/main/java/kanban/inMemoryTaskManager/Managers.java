package kanban.inMemoryTaskManager;

import kanban.inMemoryTaskManager.implementation.InMemoryHistoryManager;
import kanban.inMemoryTaskManager.implementation.InMemoryTaskManager;
import kanban.inMemoryTaskManager.interfaces.HistoryManager;
import kanban.inMemoryTaskManager.interfaces.TaskManager;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
