package uexcel.com.taskmanagerdemo.service;


import org.springframework.stereotype.Service;
import uexcel.com.taskmanagerdemo.task.TaskManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class TaskManagerService {
    private static final List<TaskManager> taskList;

    static {
        taskList = new ArrayList<>();
        taskList.add(new TaskManager(1, "uexcel","Java",
                LocalDate.now(), LocalDate.now().plusYears(2),false));
        taskList.add(new TaskManager(2,
                "uexcel","Microservices",LocalDate.now(), LocalDate.now().plusYears(2),false));
        taskList.add(new TaskManager(3,
                "uexcel","React",LocalDate.now(), LocalDate.now().plusYears(2),false));

    }

    public List<TaskManager>  findByUserName(String username){
        Predicate<TaskManager> predicate = task->task.getUsername().equalsIgnoreCase(username);
        return taskList.stream().filter(predicate).toList();
    }

    public void addTask(TaskManager task){
        int id = 0;
        for(TaskManager obj : taskList){
            if(obj.getId() > id){
              id =  obj.getId();
            }
        }
        task.setId(id+1);
        taskList.add(task);
    }

    public Optional<TaskManager> findTaskById(int taskId){
        Predicate<TaskManager> predicate = task -> task.getId() == taskId;
        return taskList.stream().filter(predicate).findFirst().or(Optional::empty);
//        for(TaskManager obj : taskList){
//            if(obj.getTaskId() == taskId){
//                return obj;
//            }
//        }
//        return null;
    }

    public void removeTaskById(int taskId){
        Predicate<TaskManager> predicate = task -> task.getId() == taskId;
                taskList.removeIf(predicate);

    }

    public void updateTask(TaskManager task) {
        for (TaskManager obj : taskList) {
            if (obj.getId() == task.getId()) {
                obj.setUsername(task.getUsername());
                obj.setDescription(task.getDescription());
                obj.setStarts(task.getStarts());
                obj.setEnds(task.getEnds());
                return;
            }
        }
    }

        public void taskDone(int taskId){
            for(TaskManager obj : taskList) {
                if (obj.getId() == taskId) {
                    obj.setStatus(toggleTaskStatus(obj.isStatus()));
                    return;
                }
            }
        }

        public boolean toggleTaskStatus(boolean taskStatus){
            return !taskStatus;
        }
    }


