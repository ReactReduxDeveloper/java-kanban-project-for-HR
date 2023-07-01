package kanban.inMemoryTaskManager.implementation;

import kanban.inMemoryTaskManager.Managers;
import kanban.inMemoryTaskManager.interfaces.HistoryManager;
import kanban.inMemoryTaskManager.interfaces.TaskManager;
import kanban.model.Epic;
import kanban.model.SubTask;
import kanban.model.Task;
import kanban.model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static kanban.model.TaskStatus.NEW;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;

    private final HashMap<Integer, Task> tasksMap;
    private final HashMap<Integer, Epic> epicsMap;
    private final HashMap<Integer, SubTask> subTasksMap;
    private final HistoryManager history;

    public InMemoryTaskManager() {
        this.tasksMap = new HashMap<>();
        this.epicsMap = new HashMap<>();
        this.subTasksMap = new HashMap<>();
        this.history = Managers.getDefaultHistory();
    }

    @Override
    public void addTask(Task task) {
        task.setId(id++);
        tasksMap.put(task.getId(), task);
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(id++);
        epicsMap.put(epic.getId(), epic);
    }

    @Override
    public void addSubTask(SubTask subTask) {
        subTask.setId(id++);
        subTasksMap.put(subTask.getId(), subTask);
        getSubTaskList(subTask.getEpicId()).add(subTask.getId());
        updateStatusEpic(subTask.getEpicId());
    }

    @Override
    public Task getTask(int id) {
        if (tasksMap.getOrDefault(id, null) != null) {
            history.addHistory(tasksMap.get(id));
            return tasksMap.get(id);
        } else {
            return null;
        }
    }

    @Override
    public Epic getEpic(int id) {
        if (epicsMap.getOrDefault(id, null) != null) {
            history.addHistory(epicsMap.get(id));
            return epicsMap.get(id);
        } else {
            return null;
        }
    }

    @Override
    public SubTask getSubTask(int id) {
        if (subTasksMap.getOrDefault(id, null) != null) {
            history.addHistory(subTasksMap.get(id));
            return subTasksMap.get(id);
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Integer> getSubTaskList(int epicId) {
        if (epicsMap.getOrDefault(epicId, null) != null) {
            return epicsMap.getOrDefault(epicId, null).getIdSubTasks();
        } else {
            return null;
        }
    }

    @Override
    public HashMap<Integer, Task> getTasksMap() {
        return new HashMap<>(tasksMap);
    }

    @Override
    public HashMap<Integer, Epic> getEpicsMap() {
        return new HashMap<>(epicsMap);
    }

    @Override
    public HashMap<Integer, SubTask> getSubTasksMap() {
        return new HashMap<>(subTasksMap);
    }

    @Override
    public void clearTask() {
        tasksMap.clear();
    }

    @Override
    public void clearEpic() {
        epicsMap.clear();
        subTasksMap.clear();
    }

    @Override
    public void clearSubTask() {
        subTasksMap.clear();
        for (Integer id : epicsMap.keySet()) {
            epicsMap.get(id).setStatus(NEW);
            epicsMap.get(id).setIdSubTasks(null);
        }
    }

    @Override
    public void updateTask(Task task) {
        if (tasksMap.containsKey(task.getId())) {
            tasksMap.put(task.getId(), task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epicsMap.containsKey(epic.getId())) {
            tasksMap.put(epic.getId(), epic);
        }
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        if (subTasksMap.containsKey(subTask.getId())) {
            subTasksMap.put(subTask.getId(), subTask);
            updateStatusEpic(subTask.getEpicId());
        }
    }

    @Override
    public void removeTask(int id) {
        if (getTask(id) != null) {
            tasksMap.remove(id);
        }
    }

    @Override
    public void removeEpic(int id) {
        if (getSubTaskList(id) != null) {
            for (int i = 0; i < getSubTaskList(id).size(); i++) {
                subTasksMap.remove(getSubTaskList(id).get(i));
            }
            epicsMap.remove(id);
        }
    }

    @Override
    public void removeSubTask(int id) {
        if (getSubTaskList(getSubTask(id).getEpicId()) != null) {
            getSubTaskList(getSubTask(id).getEpicId()).remove((Integer) id);
            updateStatusEpic(getSubTask(id).getEpicId());
            subTasksMap.remove(id);

        }
    }

    @Override
    public void updateStatusEpic(int id) {
        int statusInProgress = 0;
        int statusDone = 0;
        if (getSubTaskList(id) != null) {
            for (int i = 0; i < getSubTaskList(id).size(); i++) {
                if (getSubTask(getSubTaskList(id).get(i)).equals(TaskStatus.IN_PROGRESS)) {
                    statusInProgress++;
                } else {
                    statusDone++;
                }
            }
            if (statusInProgress < 1 && statusDone < 1) {
                getEpic(id).setStatus(NEW);
            } else if (statusInProgress > 1) {
                getEpic(id).setStatus(TaskStatus.IN_PROGRESS);
            } else {
                getEpic(id).setStatus(TaskStatus.DONE);
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        return history.getHistory();
    }
}
