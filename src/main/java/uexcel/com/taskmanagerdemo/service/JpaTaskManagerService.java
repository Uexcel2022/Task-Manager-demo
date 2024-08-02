package uexcel.com.taskmanagerdemo.service;


import org.springframework.stereotype.Service;
import uexcel.com.taskmanagerdemo.repository.TaskManagerRepository;
import uexcel.com.taskmanagerdemo.task.TaskDataValidator;
import uexcel.com.taskmanagerdemo.task.TaskManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class JpaTaskManagerService {
    private final TaskManagerRepository taskManagerRepository;
    private final TaskDataValidator taskDataValidator;

    public JpaTaskManagerService(TaskManagerRepository taskManagerRepository, TaskDataValidator taskDataValidator) {
        this.taskManagerRepository = taskManagerRepository;
        this.taskDataValidator = taskDataValidator;
    }

    public List<TaskManager>  findByUserName(String username){
        return taskManagerRepository.findByUsername(username);
    }

    public String saveTask(TaskManager task){
        String msg = taskDataValidator.validate(task);
        if (msg.equals("success")) {
            taskManagerRepository.save(task);
            return msg;
        }
        return msg;
    }

    public Optional<TaskManager> findTaskById(int id){
        return taskManagerRepository.findById(id).or(() ->
                Optional.of(new TaskManager(0, "non", "non", LocalDate.now(), LocalDate.now(), false)));
    }

    public void deleteTaskById(int id){
        taskManagerRepository.deleteById(id);
    }

    public String taskDone(int id){
        Optional<TaskManager> obj = findTaskById(id);
        if(obj.isPresent() && !obj.get().getUsername().equalsIgnoreCase("non")){
            TaskManager task = obj.get();
            task.setStatus(toggleTaskStatus(task.isStatus()));
            taskManagerRepository.save(task);
            return "success";
        }

        return "Something went wrong: Could not find task by ID="+id;
    }

        public boolean toggleTaskStatus(boolean taskStatus){
            return !taskStatus;
        }
    }


